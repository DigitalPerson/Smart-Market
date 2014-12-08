<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="ResetPassword.aspx.cs" Inherits="ResetPassword" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Reset Password</title>
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
                <td class="style1">
                    <asp:Label ID="password_Label" runat="server" Text="New Password"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="password_TextBox" runat="server" TextMode="Password" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="password_TextBox"
                        Display="Dynamic" ErrorMessage="Password is required field"></asp:RequiredFieldValidator>
                    <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="password_TextBox"
                        Display="Dynamic" ErrorMessage="Password must be at least 8 characters." ValidationExpression="^.{8,}$"></asp:RegularExpressionValidator>
                </td>
            </tr>
            <tr>
                <td class="style1">
                    <asp:Label ID="confirmPassword_Label" runat="server" Text="Confirm Password"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="confirmPassword_TextBox" runat="server" TextMode="Password" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:CompareValidator ID="CompareValidator1" runat="server" ControlToCompare="password_TextBox"
                        ControlToValidate="confirmPassword_TextBox" Display="Dynamic" ErrorMessage="These passwords don't match. Try again?"></asp:CompareValidator>
                </td>
            </tr>
        </table>
        <br />
        <br />
        <asp:Button ID="submit_Button" runat="server" OnClick="submit_Button_Click" 
            Text="Submit" CssClass="button" />
            </asp:Panel>
    </div>
</asp:Content>
