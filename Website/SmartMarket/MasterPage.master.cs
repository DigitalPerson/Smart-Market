using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class MasterPage : System.Web.UI.MasterPage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        String loginEmail = HttpContext.Current.User.Identity.Name;
        Boolean loggedIn = HttpContext.Current.User.Identity.IsAuthenticated;
        SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
        User user = db.Users.SingleOrDefault(d => d.email.Equals(loginEmail));
        if (loggedIn && user != null)
        {
            if (user.Developer != null)
            {
                manageApps_HyperLink.Visible = true;
                manageApps_HyperLink.NavigateUrl = "~/DeveloperControlPanel.aspx?id=" + user.Developer.developerID.ToString();
            }
            if (user.admin)
            {
                admin_HyperLink.Visible = true;
            }
        }
        else
        {
            profile_Label.Visible = false;
        }
    }
    protected void search_ImageButon_Click(object sender, ImageClickEventArgs e)
    {
        Response.Redirect("Search.aspx?keyword=" + search_TextBox.Text);
    }
}
