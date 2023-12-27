using Newtonsoft.Json;
using System;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace WpfApp1.Service
{
    public static class SoketsManager
    {
        public static async Task<(string,string)> sendData(string data, string messageType)
        {
            try
            {
                using (Socket socket = new Socket(SocketType.Stream, ProtocolType.Tcp))
                {
                    await socket.ConnectAsync("", 8888);
                    var message = new
                    {
                        type = messageType,
                        items = data
                    };
                    socket.Send(Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(message)));
                    socket.Shutdown(SocketShutdown.Both);
                    socket.Close();
                    return ("ok", "sended");
                }
            }
            catch(Exception e)
            {
                return ("err","error {e.Message}");
            }
        }
        public static async Task<(string, string)> RequestData(string data, string messageType)
        {
            try
            {
                using (Socket socket = new Socket(SocketType.Stream, ProtocolType.Tcp))
                {
                    await socket.ConnectAsync("", 8888);
                    var message = "";
                    var buffer = new byte[1024];
                    while (socket.Connected)
                    {
                        int bytesRead = socket.Receive(buffer, SocketFlags.None);
                        message += Encoding.UTF8.GetString(buffer, 0, bytesRead);
                        if (message.EndsWith("}"))
                        {
                            break;
                        }
                    }
                    socket.Shutdown(SocketShutdown.Both);
                    socket.Close(); 
                    return ("ok", message);
                }
            }
            catch (Exception e)
            {
                return ("err", "Ошибка при получении данных: " + e.Message);
            }
        }
    }
}
