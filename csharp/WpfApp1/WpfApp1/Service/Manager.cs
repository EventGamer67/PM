using System;
using System.Collections.Generic;
using System.IO.Packaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace WpfApp1.Service
{
    public static class Manager
    {
        public static Frame window;

        public static bool login(string pass, string name)
        {
            if (pass == "123" && name == "123")
            {
                return true;
            }
            else return false;
        }
    }
}
