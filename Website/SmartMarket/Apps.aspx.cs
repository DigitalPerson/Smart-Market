using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Data.SqlClient;

public partial class Apps : System.Web.UI.Page
{
    private SmartMarketDataClassesDataContext db;
    private String appsPath;
    private String screenshotsPath;
    private String iconsPath;
    private string qrCodesPath;
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
    protected void DeleteApp(Object sender, EventArgs e)
    {
        String packageName = apps_GridView.SelectedRow.Cells[2].Text;
        App app = db.Apps.Single(a => a.packageName.Equals(packageName));
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
            db.Apps.DeleteOnSubmit(app);
        }
        try
        {
            db.SubmitChanges();
            apps_GridView.DataBind();
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
}