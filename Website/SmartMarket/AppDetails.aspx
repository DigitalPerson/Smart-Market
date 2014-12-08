<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="AppDetails.aspx.cs" Inherits="AppDetails" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>App details</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <asp:Label ID="name_Label" runat="server" Text="Name" Font-Size="20pt" ForeColor="Yellow"
            Font-Bold="True"></asp:Label>
        <br />
        <asp:HyperLink ID="developer_HyperLink" runat="server">Developer</asp:HyperLink>

        <div>
            <div class="appLeft">
                <asp:Image ID="icon_Image" runat="server" CssClass="iconImageField" />

                <ajaxToolkit:ToolkitScriptManager ID="ToolkitScriptManager1" runat="Server" />
                <ajaxToolkit:Rating ID="averageRate_rating" runat="server" MaxRating="5" CssClass="ratingStarSmall"
                    StarCssClass="ratingItemSmall" WaitingStarCssClass="Saved" FilledStarCssClass="Filled"
                    EmptyStarCssClass="Empty" ReadOnly="True">
                </ajaxToolkit:Rating>
                <br />
                <asp:Button ID="download_Button" CssClass="button" runat="server" OnClick="download_Button_Click"
                    Text="Download" />
                <br />
                <br />
                <asp:LinkButton ID="alternativeDownloadMethod_LinkButton" runat="server" OnClick="alternativeDownloadMethod_LinkButton_Click">Alternative download method</asp:LinkButton>
                <br />
            </div>
            <div class="appRight">
           
              
                <asp:Label ID="Label15" runat="server" Text="Rates Count:"></asp:Label>
                &nbsp;<asp:Label ID="ratesCount_Label" runat="server" Text="count"></asp:Label>
                <br />
                <asp:Label ID="Label9" runat="server" Text="Updated:"></asp:Label>
                &nbsp;<asp:Label ID="date_Label" runat="server" Text="date"></asp:Label>
                <br />
                <asp:Label ID="Label10" runat="server" Text="Current Version:"></asp:Label>
                &nbsp;<asp:Label ID="versionNumber_Label" runat="server" Text="version"></asp:Label>
                <br />
                <asp:Label ID="Label11" runat="server" Text="Required Android:"></asp:Label>
                &nbsp;<asp:Label ID="minAndroidVersion_Label" runat="server" Text="min"></asp:Label>
                <br />
                <asp:Label ID="Label12" runat="server" Text="Category:"></asp:Label>
                &nbsp;<asp:Label ID="Category_Label" runat="server" Text="category"></asp:Label>
                <br />
                <asp:Label ID="Label13" runat="server" Text="Installs:"></asp:Label>
                &nbsp;<asp:Label ID="installs_Label" runat="server" Text="installs"></asp:Label>
                <br />
                <asp:Label ID="Label14" runat="server" Text="Size:"></asp:Label>
                &nbsp;<asp:Label ID="size_Label" runat="server" Text="size"></asp:Label>
                <br />
            </div>
        </div>
        &nbsp;<br />
        <asp:GridView ID="screenshots_GridView" runat="server" AllowPaging="True" 
            AutoGenerateColumns="False" DataKeyNames="screenshotID" 
            DataSourceID="screenshots_LinqDataSource" PageSize="1">
            <Columns>
                <asp:TemplateField HeaderText="Screenshot">
                    <ItemTemplate>
                        <asp:Image ID="Image1" runat="server" ImageUrl='<%# Eval("screenshotID", "Files/Screenshots/{0}") + Eval("extension", "{0}") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>
        </asp:GridView>
        <asp:LinqDataSource ID="screenshots_LinqDataSource" runat="server" 
            ContextTypeName="SmartMarketDataClassesDataContext" EntityTypeName="" 
            TableName="Screenshots" Where="App.packageName == @App">
            <WhereParameters>
                <asp:QueryStringParameter Name="App" QueryStringField="package" Type="Object" />
            </WhereParameters>
        </asp:LinqDataSource>
        <br />
        &nbsp;<br />

        <asp:Label ID="Label1" runat="server" Text="Description:"></asp:Label>
        &nbsp;<asp:Label ID="description_Label" runat="server" Text="Description"></asp:Label>
        <br />
        &nbsp;<br />
        <br />
        <asp:Label ID="Label6" runat="server" Text="Developer Website:"></asp:Label>
        &nbsp;<asp:HyperLink ID="developerWebsite_HyperLink" runat="server">website</asp:HyperLink>
        <br />
        <asp:Label ID="Label7" runat="server" Text="Developer Email:"></asp:Label>
        &nbsp;<asp:HyperLink ID="developerEmail_HyperLink" runat="server">email</asp:HyperLink>
        <br />
        <br />
        <asp:Label ID="Label8" runat="server" Text="Your Rate:"></asp:Label>
        <ajaxToolkit:Rating ID="yourRate_rating" runat="server" MaxRating="5" CssClass="ratingStarLarge"
            StarCssClass="ratingItemLarge" WaitingStarCssClass="Saved" FilledStarCssClass="Filled"
            EmptyStarCssClass="Empty" OnChanged="RateApp" AutoPostBack="True">
        </ajaxToolkit:Rating>
        <br />
        <asp:Image ID="qrCode_Image" runat="server" Width="150px" />
        <br />
    </div>
</asp:Content>
