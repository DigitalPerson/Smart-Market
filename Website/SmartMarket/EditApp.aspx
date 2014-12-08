<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="EditApp.aspx.cs" Inherits="EditApp" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Edit App</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <table style="width: 50%;">
        <tr>
            <td class="style1">
                <asp:Label ID="name_Label" runat="server" Text="Name"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="name_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                <br />
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="name_TextBox"
                    Display="Dynamic" ErrorMessage="App name is required field."></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="appType_Label" runat="server" Text="App Type"></asp:Label>
            </td>
            <td>
                <asp:DropDownList ID="appType_DropDownList" runat="server" CssClass="fields" 
                    AutoPostBack="True" DataSourceID="appTypes_LinqDataSource" DataTextField="name" 
                    DataValueField="appTypeID">
                </asp:DropDownList>
                <asp:LinqDataSource ID="appTypes_LinqDataSource" runat="server" 
                    ContextTypeName="SmartMarketDataClassesDataContext" EntityTypeName="" 
                    OrderBy="name" TableName="AppTypes">
                </asp:LinqDataSource>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="category_Label" runat="server" Text="Category"></asp:Label>
            </td>
            <td>
                <asp:DropDownList ID="category_DropDownList" runat="server" DataSourceID="categories_LinqDataSource"
                    DataTextField="name" DataValueField="categoryID" CssClass="fields">
                </asp:DropDownList>
                <asp:LinqDataSource ID="categories_LinqDataSource" runat="server" 
                    ContextTypeName="SmartMarketDataClassesDataContext" EntityTypeName="" 
                    OrderBy="name" TableName="Categories" Where="appTypeID == @appTypeID">
                    <WhereParameters>
                        <asp:ControlParameter ControlID="appType_DropDownList" DefaultValue="1" 
                            Name="appTypeID" PropertyName="SelectedValue" Type="Int32" />
                    </WhereParameters>
                </asp:LinqDataSource>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="description_Label" runat="server" Text="Description"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="description_TextBox" runat="server" TextMode="MultiLine" 
                    CssClass="fields" Height="153px"></asp:TextBox>
                <br />
                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" 
                    ControlToValidate="description_TextBox" Display="Dynamic" 
                    ErrorMessage="Description is required field"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="whatIsNew_Label" runat="server" Text="What is new"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="whatIsNew_TextBox" runat="server" TextMode="MultiLine" 
                    CssClass="fields" Height="153px"></asp:TextBox>
            </td>
        </tr>
        </table>
        <br />
        <br />
    <asp:Button ID="continue_Button" runat="server" onclick="continue_Button_Click" 
        Text="Continue" CssClass="button" />
    </div>
</asp:Content>
