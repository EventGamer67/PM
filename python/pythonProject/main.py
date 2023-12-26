import json

from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from typing import *
import socket
import sys
import threading
import json
from models import *
import jsonpickle


class MainWindow(QWidget):
    mainsocket: socket.socket
    users: List[socket.socket] = []
    items: List[Item] = []

    table_widget = None

    def __init__(self):
        super().__init__()
        self.setGeometry(300, 300, 800, 500)
        main_layout = QVBoxLayout()
        toolbar = QToolBar()
        connect_btn = QPushButton("Connect")
        title = QLabel()
        title.setText("status: online")
        toolbar.addWidget(connect_btn)
        toolbar.addWidget(title)
        main_layout.addWidget(toolbar)

        list_toolbar = QToolBar()
        reversebtn = QPushButton("Reverse")
        reversebtn.clicked.connect(self.reverse)
        list_toolbar.addWidget(reversebtn)

        addbtn = QPushButton("new")
        addbtn.clicked.connect(self.addnew)
        list_toolbar.addWidget(addbtn)

        savebtn = QPushButton("save")
        savebtn.clicked.connect(self.save)
        list_toolbar.addWidget(savebtn)

        sendbtn = QPushButton("send data")
        sendbtn.clicked.connect(self.senddata)
        list_toolbar.addWidget(sendbtn)

        main_layout.addWidget(list_toolbar)

        self.table_widget = QTableWidget()
        header: QHeaderView = self.table_widget.horizontalHeader()
        header.setSectionResizeMode(QHeaderView.Stretch)
        main_layout.addWidget(self.table_widget)

        bottom_bar = QHBoxLayout()

        btn = QPushButton("Click")
        sec_btn = QPushButton("No Click")
        bottom_bar.addWidget(btn)
        bottom_bar.addWidget(sec_btn)

        main_layout.addLayout(bottom_bar)
        self.setLayout(main_layout)

    def addnew(self):
        self.items.append(Item(name="sample",phone="sample",balance="sample",active=False))
        self.refreshTable()

    def reverse(self):
        self.table_widget.sortItems(0, order=Qt.AscendingOrder)

    def senddata(self):
        dict = {"type": "set", "items": []}
        for item in self.items:
            dict["items"].append({
                "name": item.name,
                "phone": item.phone,
                "balance": item.balance,
                "active": item.active
            })
        print(dict)
        for client in self.users:
            client.send(str(dict).encode('utf-8'))

    def save(self):
        iteration = 0
        for item in self.items:
            item.name = self.table_widget.item(iteration, 0).text()
            item.phone = self.table_widget.item(iteration, 1).text()
            item.balance = self.table_widget.item(iteration, 2).text()
            item.active = self.table_widget.item(iteration, 3).text()
            iteration += 1
        pass

    def addmessage(self, message: str):
        try:
            map = json.decoder.JSONDecoder().decode(message)
            if (map["type"] != None):
                if (map["type"] == "set"):
                    users = map["items"]
                    self.items.clear()
                    for i in users:
                        self.items.append(
                            Item(name=i["name"], phone=i["phone"], balance=i["balance"], active=i["active"]))
                    self.refreshTable()

        except Exception as err:
            print(f"invalid {err}")
        pass

    def refreshTable(self):
        self.table_widget.clear()
        self.table_widget.setRowCount(len(self.items))
        self.table_widget.setColumnCount(4)

        iteration = 0
        for item in self.items:
            name = QTableWidgetItem(item.name)
            phone = QTableWidgetItem(item.phone)
            balance = QTableWidgetItem(str(item.balance))
            active = QTableWidgetItem(str(item.active))
            self.table_widget.setItem(iteration, 0, name)
            self.table_widget.setItem(iteration, 1, phone)
            self.table_widget.setItem(iteration, 2, balance)
            self.table_widget.setItem(iteration, 3, active)
            iteration += 1
        pass

    def listenmessages(self, sock: socket.socket):
        while True:
            message = sock.recv(2048).decode('utf-8')
            if (message is not None and message != ""):
                print(f"user: {sock} sended {message}")
                self.addmessage(message)

    def connectusers(self, socket: socket.socket):
        print("Listeting")
        while True:
            conn, addr = socket.accept()
            self.users.append(conn)
            print(f'Connected {conn}')
            thread = threading.Thread(target=self.listenmessages, args=[conn], daemon=True)
            thread.start()

    def startserver(self):
        self.mainsocket = socket.socket()
        self.mainsocket.bind(('', 8888))
        self.mainsocket.listen(10)
        thread = threading.Thread(target=self.connectusers, args=[self.mainsocket], daemon=True)
        thread.start()
        pass


if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MainWindow()

    window.startserver()
    window.show()

    sys.exit(app.exec_())
