CREATE DATABASE SmartMarket;
GO
--------------------------------------------------------------------------------------------------
USE SmartMarket;
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE Country
(
	countryID INT IDENTITY PRIMARY KEY
	,abbreviation NCHAR(2) NOT NULL
	,name VARCHAR(50) NOT NULL
	,code VARCHAR(10) NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE [User]
(
	userID INT IDENTITY PRIMARY KEY
	,firstName VARCHAR(50) NOT NULL
	,lastName VARCHAR(50) NOT NULL
	,gender NCHAR(2) NOT NULL
	,countryID INT NOT NULL FOREIGN KEY REFERENCES Country(countryID)
	,email VARCHAR(50) UNIQUE NOT NULL
	,[password] VARCHAR(50) NOT NULL
	,[admin] BIT NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE Developer
(
	developerID INT PRIMARY KEY FOREIGN KEY REFERENCES [User](userID) ON DELETE CASCADE
	,name VARCHAR(50) NOT NULL
	,website VARCHAR(50) 
	,phone VARCHAR(20)
	,supportEmail VARCHAR(50)
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE AppType
(
	appTypeID INT IDENTITY PRIMARY KEY
	,name VARCHAR(50) UNIQUE NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE Category
(
	categoryID INT IDENTITY PRIMARY KEY
	,appTypeID INT NOT NULL FOREIGN KEY REFERENCES AppType(appTypeID)
	,name VARCHAR(50) NOT NULL
	,UNIQUE (appTypeID,name)
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE App
(
	appID INT IDENTITY PRIMARY KEY
	,categoryID INT NOT NULL FOREIGN KEY REFERENCES Category(categoryID)
	,developerID INT NOT NULL FOREIGN KEY REFERENCES Developer(developerID)
	,name VARCHAR(50) NOT NULL
	,packageName VARCHAR(100) UNIQUE NOT NULL
	,installs INT NOT NULL
	,[description] VARCHAR(4000) NOT NULL
	,whatIsNew VARCHAR(500)
	,rate REAL NOT NULL
	,ratesCount INT NOT NULL
	,activatedVersionID INT
	,published BIT NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE [Version]
(
	versionID INT IDENTITY PRIMARY KEY
	,appID INT NOT NULL FOREIGN KEY REFERENCES App(appID) ON DELETE CASCADE
	,versionNumber VARCHAR(50) NOT NULL
	,[size] REAL NOT NULL
	,minAndroidVersion VARCHAR(15) NOT NULL
	,[date] DATETIME NOT NULL
	,UNIQUE (appID,versionNumber)
);
GO
--------------------------------------------------------------------------------------------------
ALTER TABLE App ADD FOREIGN KEY (activatedVersionID) REFERENCES Version(versionID);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE UserApp
(
	userID INT FOREIGN KEY REFERENCES [User](userID) ON DELETE CASCADE
	,appID INT FOREIGN KEY REFERENCES App(appID) ON DELETE CASCADE
	,PRIMARY KEY (userID,appID)
	,rate REAL NOT NULL
	,favorite BIT NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE Screenshot
(
	screenshotID INT IDENTITY PRIMARY KEY
	,appID INT NOT NULL FOREIGN KEY REFERENCES App(appID) ON DELETE CASCADE
	,extension VARCHAR(5) NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE Recommendation
(
	userID INT FOREIGN KEY REFERENCES [User](userID) ON DELETE CASCADE
	,appID INT FOREIGN KEY REFERENCES App(appID) ON DELETE CASCADE
	,PRIMARY KEY (userID,appID)
	,expectedRate REAL NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
CREATE TABLE UserFeature
(
	userID INT FOREIGN KEY REFERENCES [User](userID) ON DELETE CASCADE
	,featureID INT
	,PRIMARY KEY (userID,featureID)
	,featureValue REAL NOT NULL
);
GO
--------------------------------------------------------------------------------------------------
INSERT INTO AppType VALUES ('Application');
INSERT INTO AppType VALUES ('Game');
--------------------------------------------------------------------------------------------------
INSERT INTO Category VALUES (1,'Books & Reference');
INSERT INTO Category VALUES (1,'Business');
INSERT INTO Category VALUES (1,'Comics');
INSERT INTO Category VALUES (1,'Communication');
INSERT INTO Category VALUES (1,'Education');
INSERT INTO Category VALUES (1,'Entertainment');
INSERT INTO Category VALUES (1,'Finance');
INSERT INTO Category VALUES (1,'Health & Fitness');
INSERT INTO Category VALUES (1,'Libraries & Demo');
INSERT INTO Category VALUES (1,'Lifestyle');
INSERT INTO Category VALUES (1,'Live Wallpaper');
INSERT INTO Category VALUES (1,'Media & Video');
INSERT INTO Category VALUES (1,'Medical');
INSERT INTO Category VALUES (1,'Music & Audio');
INSERT INTO Category VALUES (1,'News & Magazines');
INSERT INTO Category VALUES (1,'Personalization');
INSERT INTO Category VALUES (1,'Photography');
INSERT INTO Category VALUES (1,'Productivity');
INSERT INTO Category VALUES (1,'Shopping');
INSERT INTO Category VALUES (1,'Social');
INSERT INTO Category VALUES (1,'Sports');
INSERT INTO Category VALUES (1,'Tools');
INSERT INTO Category VALUES (1,'Transportation');
INSERT INTO Category VALUES (1,'Travel & Local');
INSERT INTO Category VALUES (1,'Weather');
INSERT INTO Category VALUES (1,'Widgets');
INSERT INTO Category VALUES (2,'Arcade & Action');
INSERT INTO Category VALUES (2,'Brain & Puzzle');
INSERT INTO Category VALUES (2,'Cards & Casino');
INSERT INTO Category VALUES (2,'Casual');
INSERT INTO Category VALUES (2,'Live Wallpaper');
INSERT INTO Category VALUES (2,'Racing');
INSERT INTO Category VALUES (2,'Sports Games');
INSERT INTO Category VALUES (2,'Widgets');
--------------------------------------------------------------------------------------------------
INSERT INTO Country VALUES ('AF','Afghanistan','93');
INSERT INTO Country VALUES ('AL','Albania','355');
INSERT INTO Country VALUES ('DZ','Algeria','213');
INSERT INTO Country VALUES ('AD','Andorra','376');
INSERT INTO Country VALUES ('AO','Angola','244');
INSERT INTO Country VALUES ('AI','Anguilla','809');
INSERT INTO Country VALUES ('AG','Antigua and Barbuda','268');
INSERT INTO Country VALUES ('AR','Argentina','54');
INSERT INTO Country VALUES ('AM','Armenia','374');
INSERT INTO Country VALUES ('AW','Aruba','297');
INSERT INTO Country VALUES ('AU','Australia','61');
INSERT INTO Country VALUES ('AT','Austria','43');
INSERT INTO Country VALUES ('AZ','Azerbaijan','994');
INSERT INTO Country VALUES ('BH','Bahrain','973');
INSERT INTO Country VALUES ('BD','Bangladesh','880');
INSERT INTO Country VALUES ('BB','Barbados','246');
INSERT INTO Country VALUES ('BY','Belarus','375');
INSERT INTO Country VALUES ('BE','Belgium','32');
INSERT INTO Country VALUES ('BZ','Belize','501');
INSERT INTO Country VALUES ('BJ','Benin','229');
INSERT INTO Country VALUES ('BM','Bermuda','809');
INSERT INTO Country VALUES ('BT','Bhutan','975');
INSERT INTO Country VALUES ('BO','Bolivia','591');
INSERT INTO Country VALUES ('BA','Bosnia And Herzegovina','387');
INSERT INTO Country VALUES ('BW','Botswana','267');
INSERT INTO Country VALUES ('BR','Brazil','55');
INSERT INTO Country VALUES ('IO','British Indian Ocean Territory','246');
INSERT INTO Country VALUES ('BN','Brunei','673');
INSERT INTO Country VALUES ('BG','Bulgaria','359');
INSERT INTO Country VALUES ('BF','Burkina Faso','226');
INSERT INTO Country VALUES ('BI','Burundi','257');
INSERT INTO Country VALUES ('KH','Cambodia','855');
INSERT INTO Country VALUES ('CM','Cameroon','237');
INSERT INTO Country VALUES ('CA','Canada','1');
INSERT INTO Country VALUES ('CV','Cape Verde','238');
INSERT INTO Country VALUES ('KY','Cayman Islands','345');
INSERT INTO Country VALUES ('TD','Chad','235');
INSERT INTO Country VALUES ('CL','Chile','56');
INSERT INTO Country VALUES ('CN','China','86');
INSERT INTO Country VALUES ('CI','Cote d''Ivoire','225');
INSERT INTO Country VALUES ('CO','Colombia','57');
INSERT INTO Country VALUES ('CG','Congo','242');
INSERT INTO Country VALUES ('CD','Congo (Dem. Rep.)','243');
INSERT INTO Country VALUES ('HR','Croatia','385');
INSERT INTO Country VALUES ('CU','Cuba','53');
INSERT INTO Country VALUES ('CY','Cyprus','357');
INSERT INTO Country VALUES ('CZ','Czech Republic','420');
INSERT INTO Country VALUES ('DK','Denmark','45');
INSERT INTO Country VALUES ('DJ','Djibouti','253');
INSERT INTO Country VALUES ('DM','Dominica','767');
INSERT INTO Country VALUES ('DO','Dominican Republic','809');
INSERT INTO Country VALUES ('TL','East Timor','670');
INSERT INTO Country VALUES ('EC','Ecuador','593');
INSERT INTO Country VALUES ('EG','Egypt','20');
INSERT INTO Country VALUES ('SV','El Salvador','503');
INSERT INTO Country VALUES ('GQ','Equatorial Guinea','240');
INSERT INTO Country VALUES ('EE','Estonia','372');
INSERT INTO Country VALUES ('FK','Falkland Islands (Malvinas)','500');
INSERT INTO Country VALUES ('FO','Faroe Islands','298');
INSERT INTO Country VALUES ('FJ','Fiji','679');
INSERT INTO Country VALUES ('FI','Finland','358');
INSERT INTO Country VALUES ('FR','France','33');
INSERT INTO Country VALUES ('GF','French Guiana','594');
INSERT INTO Country VALUES ('PF','French Polynesia','689');
INSERT INTO Country VALUES ('GA','Gabon','241');
INSERT INTO Country VALUES ('GM','Gambia','220');
INSERT INTO Country VALUES ('GP','Guadeloupe','590');
INSERT INTO Country VALUES ('GE','Georgia','995');
INSERT INTO Country VALUES ('DE','Germany','49');
INSERT INTO Country VALUES ('GH','Ghana','233');
INSERT INTO Country VALUES ('GI','Gibraltar','350');
INSERT INTO Country VALUES ('GR','Greece','30');
INSERT INTO Country VALUES ('GL','Greenland','299');
INSERT INTO Country VALUES ('GD','Grenada','473');
INSERT INTO Country VALUES ('GT','Guatemala','502');
INSERT INTO Country VALUES ('GG','Guernsey','32767');
INSERT INTO Country VALUES ('GY','Guyana','592');
INSERT INTO Country VALUES ('HT','Haiti','509');
INSERT INTO Country VALUES ('HN','Honduras','504');
INSERT INTO Country VALUES ('HK','Hong Kong','852');
INSERT INTO Country VALUES ('HU','Hungary','36');
INSERT INTO Country VALUES ('IS','Iceland','354');
INSERT INTO Country VALUES ('IN','India','91');
INSERT INTO Country VALUES ('ID','Indonesia','62');
INSERT INTO Country VALUES ('IR','Iran','98');
INSERT INTO Country VALUES ('IQ','Iraq','964');
INSERT INTO Country VALUES ('IE','Ireland','353');
INSERT INTO Country VALUES ('IM','Isle Of Man','44');
INSERT INTO Country VALUES ('IL','Israel','972');
INSERT INTO Country VALUES ('IT','Italy','39');
INSERT INTO Country VALUES ('JM','Jamaica','876');
INSERT INTO Country VALUES ('JP','Japan','81');
INSERT INTO Country VALUES ('JE','Jersey','44');
INSERT INTO Country VALUES ('JO','Jordan','962');
INSERT INTO Country VALUES ('KZ','Kazakhstan','7');
INSERT INTO Country VALUES ('KE','Kenya','254');
INSERT INTO Country VALUES ('KR','Korea;South','82');
INSERT INTO Country VALUES ('KW','Kuwait','965');
INSERT INTO Country VALUES ('KG','Kyrgyzstan','996');
INSERT INTO Country VALUES ('LV','Latvia','371');
INSERT INTO Country VALUES ('LB','Lebanon','961');
INSERT INTO Country VALUES ('LS','Lesotho','266');
INSERT INTO Country VALUES ('LR','Liberia','231');
INSERT INTO Country VALUES ('LY','Libya','218');
INSERT INTO Country VALUES ('LI','Liechtenstein','423');
INSERT INTO Country VALUES ('LT','Lithuania','370');
INSERT INTO Country VALUES ('LU','Luxembourg','352');
INSERT INTO Country VALUES ('MO','Macao','853');
INSERT INTO Country VALUES ('MK','Macedonia','389');
INSERT INTO Country VALUES ('MG','Madagascar','261');
INSERT INTO Country VALUES ('MW','Malawi','265');
INSERT INTO Country VALUES ('MY','Malaysia','60');
INSERT INTO Country VALUES ('MV','Maldives','960');
INSERT INTO Country VALUES ('ML','Mali','223');
INSERT INTO Country VALUES ('MT','Malta','356');
INSERT INTO Country VALUES ('MQ','Martinique ','596');
INSERT INTO Country VALUES ('MR','Mauritania','222');
INSERT INTO Country VALUES ('MU','Mauritius','230');
INSERT INTO Country VALUES ('MX','Mexico','52');
INSERT INTO Country VALUES ('MD','Moldova','373');
INSERT INTO Country VALUES ('MC','Monaco','33');
INSERT INTO Country VALUES ('MN','Mongolia','976');
INSERT INTO Country VALUES ('ME','Montenegro','382');
INSERT INTO Country VALUES ('MS','Montserrat','473');
INSERT INTO Country VALUES ('MA','Morocco ','212');
INSERT INTO Country VALUES ('MZ','Mozambique','258');
INSERT INTO Country VALUES ('MM','Myanmar','95');
INSERT INTO Country VALUES ('NA','Namibia','264');
INSERT INTO Country VALUES ('NP','Nepal','977');
INSERT INTO Country VALUES ('NL','Netherlands','31');
INSERT INTO Country VALUES ('AN','Netherlands Antilles','599');
INSERT INTO Country VALUES ('NC','New Caledonia','687');
INSERT INTO Country VALUES ('NZ','New Zealand','64');
INSERT INTO Country VALUES ('NI','Nicaragua','505');
INSERT INTO Country VALUES ('NE','Niger','227');
INSERT INTO Country VALUES ('NG','Nigeria','234');
INSERT INTO Country VALUES ('MP','Northern Mariana Islands','1670');
INSERT INTO Country VALUES ('NO','Norway','47');
INSERT INTO Country VALUES ('OM','Oman','968');
INSERT INTO Country VALUES ('PK','Pakistan','92');
INSERT INTO Country VALUES ('PS','Palestine','970');
INSERT INTO Country VALUES ('PA','Panama','507');
INSERT INTO Country VALUES ('PY','Paraguay','595');
INSERT INTO Country VALUES ('PE','Peru','51');
INSERT INTO Country VALUES ('PH','Philippines','63');
INSERT INTO Country VALUES ('PL','Poland','48');
INSERT INTO Country VALUES ('PT','Portugal ','351');
INSERT INTO Country VALUES ('PR','Puerto Rico','1787');
INSERT INTO Country VALUES ('QA','Qatar','974');
INSERT INTO Country VALUES ('RE','Reunion','262');
INSERT INTO Country VALUES ('RO','Romania','40');
INSERT INTO Country VALUES ('RU','Russia','7');
INSERT INTO Country VALUES ('RW','Rwanda ','250');
INSERT INTO Country VALUES ('KN','Saint Kitts and Nevis','1869');
INSERT INTO Country VALUES ('WL','Saint Lucia','1758');
INSERT INTO Country VALUES ('WV','Saint Vincent','1784');
INSERT INTO Country VALUES ('SA','Saudi Arabia','966');
INSERT INTO Country VALUES ('SN','Senegal','221');
INSERT INTO Country VALUES ('RS','Serbia','381');
INSERT INTO Country VALUES ('SC','Seychelles','248');
INSERT INTO Country VALUES ('SL','Sierra Leone','232');
INSERT INTO Country VALUES ('SG','Singapore','65');
INSERT INTO Country VALUES ('SK','Slovakia','421');
INSERT INTO Country VALUES ('SI','Slovenia','386');
INSERT INTO Country VALUES ('SO','Somalia','252');
INSERT INTO Country VALUES ('ZA','South Africa','27');
INSERT INTO Country VALUES ('ES','Spain','34');
INSERT INTO Country VALUES ('LK','Sri Lanka','94');
INSERT INTO Country VALUES ('SD','Sudan','249');
INSERT INTO Country VALUES ('SR','Suriname','597');
INSERT INTO Country VALUES ('SZ','Swaziland','268');
INSERT INTO Country VALUES ('SE','Sweden','46');
INSERT INTO Country VALUES ('CH','Switzerland','41');
INSERT INTO Country VALUES ('SY','Syria','963');
INSERT INTO Country VALUES ('TW','Taiwan','886');
INSERT INTO Country VALUES ('TJ','Tajikistan','7');
INSERT INTO Country VALUES ('TZ','Tanzania','255');
INSERT INTO Country VALUES ('TH','Thailand','66');
INSERT INTO Country VALUES ('TG','Togo','228');
INSERT INTO Country VALUES ('TO','Tonga','676');
INSERT INTO Country VALUES ('TT','Trinidad And Tobago','1868');
INSERT INTO Country VALUES ('TN','Tunisia','216');
INSERT INTO Country VALUES ('TR','Turkey','90');
INSERT INTO Country VALUES ('TM','Turkmenistan','993');
INSERT INTO Country VALUES ('UG','Uganda','256');
INSERT INTO Country VALUES ('UA','Ukraine','380');
INSERT INTO Country VALUES ('AE','United Arab Emirates','971');
INSERT INTO Country VALUES ('GB','United Kingdom','44');
INSERT INTO Country VALUES ('US','United States','1');
INSERT INTO Country VALUES ('UY','Uruguay','598');
INSERT INTO Country VALUES ('UZ','Uzbekistan','7');
INSERT INTO Country VALUES ('VU','Vanuatu','678');
INSERT INTO Country VALUES ('VE','Venezuela','58');
INSERT INTO Country VALUES ('VN','Viet Nam','84');
INSERT INTO Country VALUES ('VG','Virgin Islands;British','1284');
INSERT INTO Country VALUES ('WF','Wallis And Futuna','681');
INSERT INTO Country VALUES ('YE','Yemen','381');
INSERT INTO Country VALUES ('ZM','Zambia','260');
INSERT INTO Country VALUES ('ZW','Zimbabwe ','263');
--------------------------------------------------------------------------------------------------

