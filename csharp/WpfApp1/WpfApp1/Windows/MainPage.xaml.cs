using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using WpfApp1.Models;

namespace WpfApp1.Windows
{
    /// <summary>
    /// Interaction logic for MainPage.xaml
    /// </summary>
    public partial class MainPage : Page
    {
        public MainPage()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            //connect
            loadData();
        }

        private async void loadData()
        {
            try
            {
                (string res, string data) = await Service.SoketsManager.RequestData("get", "get");
                var items = JsonConvert.DeserializeObject(res)[["items"];
                JsonConvert.DeserializeObject<Item>(data);
                

            }
            catch (Exception ex) 
            {
                MessageBox.Show($"error get data {ex}");
            }
        }
    }
}
