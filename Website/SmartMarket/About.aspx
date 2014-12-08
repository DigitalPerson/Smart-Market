<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="About.aspx.cs" Inherits="Info" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>About</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:Label ID="Label3" runat="server" ForeColor="Yellow" Text="DigitalSoft" 
            Font-Size="30pt" Font-Bold="True"></asp:Label>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:Label ID="Label1" runat="server" Text="Email: support@digital-soft.org"></asp:Label>
        <br />
        <br />
        <asp:Label ID="Label2" runat="server" Text="website: www.digital-soft.org"></asp:Label>
    </div>
</asp:Content>
