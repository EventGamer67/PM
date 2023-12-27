from main import *
import sys
def test_adding_default():
    item : Item = Item(name="sample",phone="sample",balance="sample",active=False)

    app = QApplication(sys.argv)
    window = MainWindow()
    window.addnew()
    app.quit()
    assert window.items[-1].name == item.name
