from PyQt5.QtWidgets import *
from PyQt5.uic import *
import sys

class MainWindow(QWidget):
    def __init__(self):
        super().__init__()

        Mainlayout = QVBoxLayout()

        toolbar = QToolBar()
        connectBtn = QPushButton("Connect")
        title = QLabel()
        title.setText("status: ")
        toolbar.addWidget(connectBtn)
        toolbar.addWidget(title)
        Mainlayout.addWidget(toolbar)

        listToolbar = QToolBar()
        listToolbar.addWidget(QPushButton("reverse"))
        Mainlayout.addWidget(listToolbar)

        list = QListWidget()
        item = QListWidgetItem()
        item.setText("123")
        list.addItem(item)

        bottomBar = QHBoxLayout()

        btn = QPushButton("click")
        secbtn = QPushButton("no click")
        bottomBar.addWidget(btn)
        bottomBar.addWidget(secbtn)

        listlayout = QVBoxLayout()
        listlayout.addWidget(list)

        Mainlayout.addLayout(listlayout)
        Mainlayout.addLayout(bottomBar)

        self.setLayout(Mainlayout)


if __name__ == '__main__':

    app = QApplication(sys.argv)

    window = MainWindow()
    window.show()

    sys.exit(app.exec_())

