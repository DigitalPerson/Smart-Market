<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="Login.aspx.cs" Inherits="Login" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Login</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:Panel ID="Panel1" runat="server" DefaultButton="submit_Button">
            <table style="width: 50%;">
                <tr>
                    <td>
                        <asp:Label ID="email_Label" runat="server" Text="Email"></asp:Label>
                    </td>
                    <td>
                        <asp:TextBox ID="email_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                        <br />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="email_TextBox"
                            Display="Dynamic" ErrorMessage="Email is required field"></asp:RequiredFieldValidator>
                        <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="email_TextBox"
                            Display="Dynamic" ErrorMessage="Email isn't valid." ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"></asp:RegularExpressionValidator>
                    </td>
                </tr>
                <tr>
                    <td>
                        <asp:Label ID="password_Label" runat="server" Text="Password"></asp:Label>
                    </td>
                    <td>
                        <asp:TextBox ID="password_TextBox" runat="server" TextMode="Password" CssClass="fields"></asp:TextBox>
                        <br />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="password_TextBox"
                            Display="Dynamic" ErrorMessage="Password is required field"></asp:RequiredFieldValidator>
                    </td>
                </tr>
            </table>
            <br />
            <br />
            <asp:Button ID="submit_Button" runat="server" OnClick="submit_Button_Click" Text="Submit"
                CssClass="button" />
            &nbsp;
            <asp:CheckBox ID="rememberMe_CheckBox" runat="server" Checked="True" Text="Remember me" />
            &nbsp;&nbsp;
            <asp:HyperLink ID="forgotPassword_HyperLink" runat="server" NavigateUrl="~/ForgotPassword.aspx">Forgot Password?</asp:HyperLink>
        </asp:Panel>
    </div>
</asp:Content>
