using System.ComponentModel;
using WpfApp1.Service;

namespace Tests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void AuthorizationAdminCheck_returnTrue()
        {
            string pass = "123";
            string name = "123";

            Assert.IsTrue(Manager.login(pass, name));
        }

        [TestMethod]
        public void AuthorizationCheck_returnFalse()
        {
            string pass = "321";
            string name = "321";

            Assert.IsFalse(Manager.login(pass, name));
        }
    }
}