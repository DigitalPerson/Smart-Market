using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.IO;

public partial class Users : System.Web.UI.Page
{
    private SmartMarketDataClassesDataContext db;
    private String appsPath;
    private String screenshotsPath;
    private String iconsPath;
    private String qrCodesPath;
    protected void Page_Load(object sender, EventArgs e)
    {
        appsPath = Server.MapPath(Config.appsPathNotMapped);
        screenshotsPath = Server.MapPath(Config.screenshotsPathNotMapped);
        iconsPath = Server.MapPath(Config.iconsPathNotMapped);
        qrCodesPath = Server.MapPath(Config.qrCodesPathNotMapped);
        String loginEmail = HttpContext.Current.User.Identity.Name;
        db = new SmartMarketDataClassesDataContext();
        User user = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
        if (user == null || !user.admin)
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
    }
    protected void DeleteUser(Object sender, EventArgs e)
    {
        String email = users_GridView.SelectedRow.Cells[0].Text;
        User user = db.Users.Single(u => u.email.Equals(email));
        if (user.Developer != null)
        {
            foreach (var app in user.Developer.Apps)
            {
                app.activatedVersionID = null;
                db.SubmitChanges();
                foreach (var version in app.Versions)
                {
                    String filePath = appsPath + version.versionID + ".apk";
                    File.Delete(filePath);
                    db.Versions.DeleteOnSubmit(version);
                }
                foreach (var screenshoot in app.Screenshots)
                {
                    String filePath = screenshotsPath + screenshoot.screenshotID + screenshoot.extension;
                    File.Delete(filePath);
                    db.Screenshots.DeleteOnSubmit(screenshoot);
                }
                foreach (var userApp in app.UserApps)
                {
                    db.UserApps.DeleteOnSubmit(userApp);
                }
                {
                    String iconFilePath = iconsPath + app.appID + ".png";
                    File.Delete(iconFilePath);
                    String qrCodeFilePath = qrCodesPath + app.appID + ".png";
                    File.Delete(qrCodeFilePath);
                }
            }
            db.Apps.DeleteAllOnSubmit(user.Developer.Apps);
            db.Developers.DeleteOnSubmit(user.Developer);
        }
        foreach (var userApp in user.UserApps)
        {
            db.UserApps.DeleteOnSubmit(userApp);
        }
        db.Users.DeleteOnSubmit(user);   
        try
        {
            db.SubmitChanges();
            users_GridView.DataBind();
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
}