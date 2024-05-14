create database FoxGuard
GO

use FoxGuard
GO

Create table EVENTSINFO(
eventId int primary key, 
eventName varchar(45),
eventType varchar(20),
eventDate datetime,
eventLocation varchar(100),
eventDescription varchar(250)
) 
GO 

Create table ATTENDEES(
attendeeId int primary key, 
studentName varchar(45),
studentId varchar(5),
academicProgram varchar(5),
semester varchar(1),
email varchar(50)
) 
GO

Create table ATTENDANCE(
attendantId int primary key not null, 
atendeeId int references ATTENDEES(attendeeId),
eventId int references EVENTSINFO(eventId),
entryTime datetime,
exitTime datetime
) 
GO 

Create table STAFF(
memberId int not null primary key, 
enrollment int not null,
pass varchar (20) not null
) 
GO

drop database FoxGuard
drop table EVENTSINFO
drop table ATTENDEES
drop table ATTENDANCE
drop table STAFF


SET LANGUAGE 'English'
SET DATEFORMAT ymd
SET ARITHABORT, ANSI_PADDING, ANSI_WARNINGS, CONCAT_NULL_YIELDS_NULL, QUOTED_IDENTIFIER, ANSI_NULLS, NOCOUNT ON
SET NUMERIC_ROUNDABORT, IMPLICIT_TRANSACTIONS, XACT_ABORT OFF
GO

--
-- Backing up database FoxGuard
--
--
-- Create backup folder
--
IF OBJECT_ID('xp_create_subdir') IS NOT NULL
  EXEC xp_create_subdir N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\Backup'
--
-- Backup database to the file with the name: <database_name>_<yyyy>_<mm>_<dd>_<hh>_<mi>.bak
--
DECLARE @db_name SYSNAME
SET @db_name = N'FoxGuard'

DECLARE @filepath NVARCHAR(4000)
SET @filepath =
/*define base part*/ N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\Backup\' + @db_name + '_' +
/*append date*/ REPLACE(CONVERT(NVARCHAR(10), GETDATE(), 102), '.', '_') + '_' +
/*append time*/ REPLACE(CONVERT(NVARCHAR(5), GETDATE(), 108), ':', '_') + '.bak'

DECLARE @SQL NVARCHAR(MAX)
SET @SQL = 
    N'BACKUP DATABASE ' + QUOTENAME(@db_name) + ' TO DISK = @filepath WITH INIT' + 
      CASE WHEN EXISTS(
                SELECT value
                FROM sys.configurations WITH (NOLOCK)
                WHERE name = 'backup compression default'
          )
        THEN ', COMPRESSION'
        ELSE ''
      END

EXEC sys.sp_executesql @SQL, N'@filepath NVARCHAR(4000)', @filepath = @filepath
GO

USE FoxGuard
GO

IF DB_NAME() <> N'FoxGuard' SET NOEXEC ON
GO
--
-- Delete data from the table 'dbo.STAFF'
--
TRUNCATE TABLE dbo.STAFF
GO
--
-- Delete data from the table 'dbo.ATTENDANCE'
--
TRUNCATE TABLE dbo.ATTENDANCE
GO
--
-- Delete data from the table 'dbo.EVENTSINFO'
--
DELETE dbo.EVENTSINFO
GO
--
-- Delete data from the table 'dbo.ATTENDEES'
--
DELETE dbo.ATTENDEES
GO

--
-- Inserting data into table dbo.ATTENDEES
--
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (1, 'Stevie Frizzell', '11638', 'ICE', '4', 'Conrad_Frizzell61@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (2, 'Jackeline Acker', '86148', NULL, '4', 'Schuler@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (3, 'Twila Bock', '52432', NULL, '7', 'ZapataF@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (4, 'Reina Adkins', '37879', '', '6', 'Sanford549@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (5, 'Lavonia Whiteside', '87267', '', '2', 'Barr@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (6, 'Kendrick Naranjo', '24206', NULL, '4', 'Almanza@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (7, 'Kim Desantis', '22554', 'ICC', '1', 'Ron.Irish@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (8, 'Raul Palma', '93854', 'ICC', '2', 'ktercnnp_okcisx@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (9, 'Lorette Ortiz', '99298', '', '7', 'mnksgeiq8021@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (10, 'Jewel Plummer', '74883', 'ICE', '4', 'Atwood@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (11, 'Carter Matlock', '39508', 'IE', '8', 'Sima_Call6@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (12, 'Clemente Craddock', '14355', 'IER', '9', 'Kirk.HKoehler@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (13, 'Rolland Watson', '12252', NULL, '3', 'EdmundBenitez631@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (14, 'Kayleen Hogg', '78280', 'IE', '9', 'Salley69@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (15, 'Loise Mayers', '05228', 'IDGC', '6', 'Kiefer@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (16, 'Marine Turley', '46921', 'IER', '9', 'ElmerFoley@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (17, 'Debby Concepcion', '19089', '', '2', 'Brinkman28@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (18, 'Kent Staton', '10306', 'ICE', '6', 'Almeida24@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (19, 'Emmett Moulton', '90143', 'ICC', '1', 'LambF@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (20, 'Clarinda Acker', '66686', 'IDGC', '5', 'ShandraCarranza@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (21, 'Darrick Cardoza', '63360', 'ICC', '2', 'JeraldAbraham529@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (22, 'Kecia Adcock', '94854', 'IE', '4', 'Abel5@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (23, 'Maurice Mireles', '49469', 'IDGC', '5', 'Bachman@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (24, 'Ruben Simpson', '38558', 'IM', '3', 'FriedaAbreu99@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (25, 'Britt Malley', '53732', NULL, '9', 'Barrett_Mcgowan@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (26, 'Jamie Creech', '40899', NULL, '8', 'rvxkhcgk5666@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (27, 'Luis Cruse', '08240', 'ICE', '6', 'Dodge@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (28, 'Carol Abrams', '32676', 'ICC', '6', 'Perkins@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (29, 'Adah Adair', '45340', 'ICE', '7', 'NorrisBolt@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (30, 'Henry Crump', '78495', 'IDGC', '7', 'Barron@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (31, 'Magdalena Ogle', '83425', NULL, '1', 'Duval@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (32, 'Donovan Allison', '21092', '', '6', 'Zenobia.Brunson@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (33, 'Jeremy Aranda', '80380', '', '2', 'Adela_Alley61@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (34, 'Camie Burch', '12386', 'ICE', '1', 'Abel313@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (35, 'Robbie Canty', '04823', 'IE', '9', 'Suggs@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (36, 'Lavone Amos', '79250', 'ICE', '3', 'Acker@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (37, 'Johnnie Allard', '87606', NULL, '2', 'aweyzjpd3111@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (38, 'Debbra Acuna', '19554', 'IM', '4', 'Arthur@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (39, 'Abdul Bergstrom', '05910', '', '7', 'Ruffin@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (40, 'Nathanael Medina', '86604', 'ICC', '2', 'EleanoreYEscamilla158@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (41, 'Kasey Savoy', '71062', 'IDGC', '8', 'JerroldSAkins@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (42, 'Raphael Beckman', '94481', 'ICC', '3', 'Tackett@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (43, 'Eugenia Johansen', '67681', 'IER', '7', 'Chasidy.Nye82@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (44, 'Merissa Minter', '96555', 'ICC', '5', 'wawwcqng.wmbvidy@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (45, 'Alejandro Norwood', '01700', NULL, '5', 'Abney@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (46, 'Enoch Dortch', '68061', '', '3', 'Joseph.Trapp@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (47, 'Kathleen Burnside', '51563', 'ICE', '1', 'Calkins615@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (48, 'Stevie Vallejo', '87529', 'IER', '1', 'AntoineLittlefield@nowhere.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (49, 'Gilda Abney', '26966', 'ICC', '1', 'jfhbwpxk.jxmonkyjvl@example.com')
INSERT dbo.ATTENDEES(attendeeId, studentName, studentId, academicProgram, semester, email) VALUES (50, 'Pearlene Starnes', '17499', 'ICE', '7', 'pqjndav80@example.com')
GO

--
-- Inserting data into table dbo.EVENTSINFO
--
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (1, 'Expo CETYS', 'Conferencia', '1976-02-05 11:27:09.610', '3707 South Market Hwy, Indianapolis, Indiana, 26120', 'Excepturi sed et enim. Placeat numquam voluptatem sed laborum. Assumenda ratione et sed facilis quis? Aut et facere.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (2, 'Expo CETYS', 'Deportes', '2019-12-12 08:32:18.060', '3080 W Waterview Hwy, Oklahoma City, OK, 01413', 'Libero qui impedit. Repudiandae dolorem natus? Adipisci magnam inventore. Rerum ipsum commodi; obcaecati ullam similique. Eos dolores eveniet. Consequatur.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (3, 'Día del estudiante', 'Exposición', '2022-04-24 15:46:22.070', '609 East Rushwood Court, Kearns Bldg, Little Rock, AR, 92652', 'Temporibus excepturi quas. Expedita qui aut; et voluptas tempora. Nisi molestias neque. Aut magnam officia. Unde aut assumenda. Corrupti sed dignissimos.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (4, 'Día de Egresados', 'Exposición', '1970-10-26 05:06:21.420', '238 North Mountain Loop, Helena, MT, 08007', 'Odio iste placeat dolorem velit esse. Est aliquam ut a? Velit laborum dolor et. Mollitia eaque quos dolorem quia doloribus.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (5, 'Tiro con arco', 'Deportes', '2011-03-11 05:31:19.120', NULL, 'Quis iste pariatur. Magnam qui quia. Inventore nemo sit. Temporibus nemo totam. Culpa soluta consequatur. Et sed sit. Molestiae quibusdam voluptatem; ad.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (6, 'Básquetbol', 'Deportes', '1982-08-31 03:03:11.090', '', 'Dolorem iste ea. Est impedit ut? Culpa quia ratione. Perspiciatis nihil veniam. Vel et error. Laborum qui fugit. At recusandae ut.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (7, 'Expo CETYS', 'Deportes', '1989-10-12 11:00:38.650', '523 Edgewood Ct, Salt Lake City, UT, 51686', 'Voluptatem voluptate tempora. Omnis quia omnis! Quis quaerat quibusdam. Doloremque natus iste. Possimus iste amet. Repellendus repellat est. Vitae!')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (8, 'Fútbol Americano', 'Conferencia', '2005-03-10 00:47:50.090', '3040 Fox Hill Ct, MidAmerican Bldg, Columbia, SC, 74842', 'Deserunt sit et. Officia ut explicabo. Voluptatem eveniet natus! Asperiores minus voluptatem? Tempore natus vitae. Dolores architecto quia; illum nobis?')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (9, 'Básquetbol', 'Exposición', '1984-01-13 22:30:37.640', '82 Rock Hill Rd, Baton Rouge, LA, 02620', 'Est earum sit ut ullam itaque eligendi.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (10, 'Día de Egresados', 'Exposición', '2015-08-13 05:06:42.680', NULL, 'Quidem explicabo quia. Nisi porro deleniti. Quia est esse. Quasi corrupti voluptatem! Tempore ullam voluptatibus. Sed sit quo. Quo voluptatem qui. Distinctio.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (11, 'Expo CETYS', 'Conferencia', '2017-12-10 21:58:52.200', '662 Hidden Church Loop, Austin, TX, 91304', 'Qui molestias commodi vitae dignissimos impedit. Exercitationem atque sunt cupiditate quas reprehenderit et. Voluptatem repellat dolorum. Voluptatem quae qui.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (12, 'Fútbol', 'Conferencia', '1998-06-21 17:50:48.780', '', 'Veniam et voluptatum. Hic fugiat corporis voluptates consequatur? Earum dolorem architecto voluptatem error libero. Debitis ut omnis. Nulla natus vero.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (13, 'Básquetbol', 'Deportes', '1974-12-31 12:34:53.640', '3500 Ironwood Blvd, Fisher Bldg, Honolulu, HI, 71721', 'Quam aliquid amet expedita modi rem veniam.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (14, 'Día del estudiante', 'Exposición', '1981-08-23 20:59:54.370', '2490 Hidden Meadowview Loop, 1st Floor, Jefferson City, MO, 74975', 'Maxime eaque totam minima officiis laborum. Unde voluptatem ratione non cumque eligendi! Beatae perferendis consequuntur eius excepturi aut doloremque.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (15, 'Fútbol Americano', 'Exposición', '2009-07-16 13:25:09.160', '1678 N Monument Ave, Jackson, Mississippi, 86777', 'Error accusantium blanditiis delectus. Omnis iste optio autem labore. Natus consequatur ratione porro. Quidem quibusdam adipisci!')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (16, 'Trascendencias', 'Deportes', '1973-04-16 02:48:46.240', '3022 East Market Circle, Columbus, OH, 37568', 'Enim culpa deleniti sit enim perspiciatis ut. Molestias laboriosam omnis rerum velit qui omnis.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (17, 'Fútbol', 'Exposición', '1987-07-11 11:31:46.510', '2563 Red Social Ave, APT 143, Phoenix, Arizona, 67534', 'Voluptas tenetur sit ab quis molestias omnis. Unde fugit eum omnis totam non quo.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (18, 'Básquetbol', 'Exposición', '2016-06-27 06:35:41.910', '3575 Red Rock Hill Ct, 3rd Floor, Pierre, SD, 89000', 'Voluptatem rerum et mollitia dolores commodi aut. Ut nemo id? Laborum voluptatem enim! Reiciendis qui rerum. Animi voluptates iste. Omnis alias...')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (19, 'Tiro con arco', 'Conferencia', '2001-05-29 03:07:37.400', '3998 Highland Loop, Augusta, Maine, 97437', 'Et culpa quaerat error quia officia. Ratione ipsum voluptatem ut sit sed! Voluptas non eligendi officia ad ex natus. Necessitatibus excepturi in!')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (20, 'Expo CETYS', 'Exposición', '1984-09-21 07:53:57.070', '11 Front Way, 1st Floor, Nashville, TN, 29288', 'Et omnis aut; quisquam eligendi cum; aperiam ipsam soluta. Quia temporibus totam? Sed natus ipsa. Neque sint cumque. Deleniti nam voluptatum! Facere est.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (21, 'Trascendencias', 'Exposición', '1981-02-28 04:46:20.710', NULL, 'Perferendis nihil aut. Voluptatem autem est consequatur numquam quia natus. Aspernatur consequatur aut voluptas aspernatur vel nihil.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (22, 'Básquetbol', 'Conferencia', '2003-11-02 09:41:41.580', '1366 Old Riddle Hill Way, Buhl Building, Atlanta, GA, 36882', 'Expedita aut provident dolorem omnis molestiae. Officia natus iste. Cumque soluta tempore. Blanditiis rerum eum. Error molestiae facilis. Aliquam rem nam.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (23, 'Conferencia', 'Deportes', '2007-04-04 13:41:28.450', '3466 Chapel Hill Pkwy, Duke Energy Building, Lincoln, NE, 93850', 'Voluptas reiciendis ullam. Magni voluptatem perspiciatis. Nemo aut molestiae? Aspernatur maiores consequatur. Blanditiis odit vitae. Aut enim est; deleniti.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (24, 'Básquetbol', 'Exposición', '1981-07-18 06:27:32.840', NULL, 'Aut voluptatum natus consequatur illo laborum ea.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (25, 'Expo CETYS', 'Deportes', '1983-05-19 06:27:50.580', '', 'Autem eius dolor perspiciatis sit quibusdam quod; et nisi repellendus qui sint ea provident.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (26, 'Día de Egresados', 'Conferencia', '2006-05-20 22:43:08.850', '380 North Fox Hill Loop, Sacramento, California, 85419', 'Placeat nisi vitae. Vel maxime animi impedit voluptatem velit nihil. Libero dolorum exercitationem aliquid aut sed eum. Voluptas ut sit.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (27, 'Expo CETYS', 'Deportes', '2011-01-03 02:49:49.680', '3218 East Hunting Hill Avenue, Columbus, OH, 20032', 'Unde reprehenderit eum voluptatem nobis sunt dolor.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (28, 'Básquetbol', 'Deportes', '1992-04-15 02:29:37.940', '', 'Quia numquam ipsa saepe vel natus. Consectetur rerum impedit. At fuga aspernatur; voluptas omnis laboriosam. Soluta velit repellendus. Quisquam illo eius.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (29, 'Conferencia', 'Deportes', '1992-01-02 19:54:59.140', '1255 Meadowview Court, STE 1281, Denver, CO, 14016', 'A sed ex rerum consectetur eaque blanditiis. Repudiandae ut totam enim! Expedita vitae eius qui. Molestiae dolor libero odit sed perspiciatis.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (30, 'Día de Egresados', 'Deportes', '1995-08-13 11:24:38.320', '3595 Red Ashwood Highway, APT 490, Juneau, AK, 66435', 'Et ipsam sit est sequi qui ipsam. Sit sed accusantium voluptatem sed et corrupti.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (31, 'Expo CETYS', 'Conferencia', '1975-11-14 00:05:36.330', '63 Fox Hill Lane, Columbus, Ohio, 83243', 'Cumque harum labore nobis beatae. Sit perspiciatis sed cupiditate sunt obcaecati; necessitatibus veniam quam qui ut. Deserunt et aliquid! Natus est.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (32, 'Día del estudiante', 'Exposición', '2010-06-12 06:36:24.980', '1351 White Hazelwood Parkway, 3rd Floor, Springfield, Illinois, 77085', 'Ducimus tenetur velit. Odio sed aspernatur! Deleniti corporis dignissimos. Ut unde natus? In dignissimos totam. Dolor perspiciatis consectetur. Facilis;')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (33, 'Día de Egresados', 'Exposición', '1970-11-08 11:36:07.540', '617 Prospect Hill Ln, Des Moines, IA, 18375', 'Vel excepturi qui et qui natus vero. Consequatur et ut! Velit ipsum quia? Accusantium voluptas iste. Consequatur harum neque. Iusto voluptas sit.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (34, 'Fútbol', 'Conferencia', '1986-02-20 06:07:31.410', '395 Rock Hill Road, Frankfort, Kentucky, 88250', 'Voluptatem obcaecati unde aut repellendus sit numquam; unde ab laborum error eos doloremque nobis.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (35, 'Expo CETYS', 'Exposición', '1989-09-27 00:34:25.190', '622 Hidden Ski Hill Ct, Boston, MA, 23438', 'Aliquid sit totam ipsa doloribus blanditiis nulla. Ut alias veritatis deleniti velit facilis cupiditate.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (36, 'Día de Egresados', 'Deportes', '1972-06-20 16:26:51.000', '2117 South Farmview Circle, Juneau, Alaska, 04226', 'Voluptates error nobis iure debitis. Quia qui saepe repudiandae labore nesciunt! Consectetur ut totam. Incidunt magni dignissimos. Dolorem perspiciatis.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (37, 'Día del estudiante', 'Exposición', '1994-11-27 15:14:13.040', NULL, 'Asperiores omnis et cupiditate aperiam minima qui. Quas pariatur tempora. Rem iste pariatur quisquam eaque voluptatem veniam! Sint voluptatem libero.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (38, 'Trascendencias', 'Conferencia', '1987-07-04 18:21:27.730', '2532 1st Ln, Denver, Colorado, 31725', 'Natus est et quam laudantium. Perferendis nihil quas maxime vero dolorem nulla. Voluptas alias sed nesciunt dolor repellendus soluta.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (39, 'Fútbol Americano', 'Conferencia', '2007-04-07 11:32:44.130', '1965 Riverside Highway, Carson City, NV, 20587', 'Reprehenderit veritatis nisi. Recusandae dolorem voluptas. Delectus vel ad. Aliquam sit voluptatem! Modi facilis possimus. Consequatur ut officia. Saepe id!')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (40, 'Expo CETYS', 'Conferencia', '2003-02-14 20:36:07.090', '591 E Chapel Hill Way, Des Moines, IA, 80172', 'Excepturi dolorem suscipit fugiat ut nulla dolores.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (41, 'Conferencia', 'Deportes', '2018-08-17 08:50:51.250', '205 New Oak Blvd, Albany, New York, 11805', 'Voluptas labore quod. Sit quam non? Sit qui aliquam qui ut. Ipsam eos harum aut fuga illo. Dolorum modi sunt sit.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (42, 'Día de Egresados', 'Exposición', '1972-10-01 07:57:15.260', '', 'Ut officia minima ex est qui dolor...')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (43, 'Expo CETYS', 'Exposición', '1987-01-24 05:51:51.020', '61 Glenwood Loop, Buhl Building, Juneau, AK, 11862', 'Et voluptatem delectus; assumenda a expedita. Possimus et dolorum; consequuntur quo quisquam. Ut iste dolor. Non enim sed? Aspernatur id sapiente.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (44, 'Día del estudiante', 'Exposición', '2014-10-05 06:22:12.840', NULL, 'Neque est omnis. Amet quas sed. Veritatis voluptatibus quos. Non quia voluptatem. Est unde sed. Nostrum nihil explicabo. Rem sit et. Beatae vel fuga? Omnis!')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (45, 'Fútbol', 'Deportes', '2003-04-01 16:47:33.580', '1291 2nd Parkway, Standard Bldg, Honolulu, HI, 80561', 'Ut vel error est iste odit voluptatem. Officia rerum qui. Eligendi provident a omnis inventore quisquam aliquam.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (46, 'Día del estudiante', 'Deportes', '2017-02-08 12:59:05.570', NULL, 'Tempore quae et. Animi vel iste. Sunt minus voluptates. Ipsa quaerat enim; et qui aperiam. Beatae deleniti expedita! Quam nemo quis.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (47, 'Día de Egresados', 'Conferencia', '2013-06-16 23:23:45.210', '81 Riverview Lane, Appartment 2, Harrisburg, Pennsylvania, 19130', 'Amet corrupti amet. Dolorem in praesentium! Architecto cupiditate dolor. Iste ullam earum! Voluptates sunt voluptatem. Iusto non temporibus? Unde saepe ex.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (48, 'Básquetbol', 'Deportes', '2022-12-11 06:25:09.250', '54 E Hope Loop, Trenton, New Jersey, 89734', 'Rerum quia quis quos. Maxime laborum id magni nemo voluptatem consequatur. Totam commodi qui quae omnis perspiciatis nostrum.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (49, 'Fútbol Americano', 'Exposición', '1976-11-21 12:33:27.510', NULL, 'Perspiciatis dicta blanditiis. Molestiae labore nisi. Reiciendis quis autem! Distinctio error consequatur. Eius et aliquid. Natus quam ut. Quia unde qui.')
INSERT dbo.EVENTSINFO(eventId, eventName, eventType, eventDate, eventLocation, eventDescription) VALUES (50, 'Día de Egresados', 'Conferencia', '2023-01-29 10:42:35.660', '2694 Rushwood Lane, Austin, Texas, 38739', 'Autem natus unde molestias perspiciatis sed debitis.')
GO

--
-- Inserting data into table dbo.ATTENDANCE
--
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (1, 1, 1, '2003-10-25 19:13:26.190', '1992-12-07 01:31:46.120')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (2, 2, 2, '2000-06-24 14:53:32.590', '2005-05-25 03:36:50.060')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (3, 3, 3, '1989-06-24 20:28:36.700', '1989-01-02 18:13:53.000')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (4, 4, 4, '2009-08-30 07:30:59.920', '2013-08-24 23:57:08.950')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (5, 5, 5, '2003-11-27 09:59:28.490', '1991-08-05 00:48:15.580')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (6, 6, 6, '1971-08-05 16:04:33.350', '1997-08-14 10:40:30.300')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (7, 7, 7, '2006-07-05 16:55:40.770', '2015-12-17 17:45:31.760')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (8, 8, 8, '1986-09-09 14:25:54.110', '2002-09-12 17:30:07.700')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (9, 9, 9, '2007-09-16 18:40:44.470', '1989-09-03 06:41:33.210')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (10, 10, 10, '2012-10-31 23:24:41.330', '1985-03-23 06:01:15.690')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (11, 11, 11, '2013-08-27 00:15:14.170', '2016-05-14 05:30:35.100')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (12, 12, 12, '2021-05-25 02:02:57.890', '2006-11-19 05:49:05.330')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (13, 13, 13, '1971-11-28 08:33:17.290', '1998-11-15 23:48:07.640')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (14, 14, 14, '1977-12-28 10:23:23.070', '2015-09-10 20:02:27.330')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (15, 15, 15, '2012-09-12 13:48:41.870', '1989-03-31 18:52:30.190')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (16, 16, 16, '2017-01-29 16:59:08.180', '1983-10-22 08:28:27.920')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (17, 17, 17, '1995-05-01 13:48:46.290', '2023-04-27 08:54:05.520')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (18, 18, 18, '1998-04-26 07:51:34.130', '2023-09-29 14:40:58.980')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (19, 19, 19, '1988-06-02 20:43:44.200', '2015-12-02 08:19:19.770')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (20, 20, 20, '2018-03-30 13:01:55.000', '1975-11-22 00:54:26.320')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (21, 21, 21, '1987-03-17 21:20:33.050', '2015-11-07 08:28:34.070')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (22, 22, 22, '2021-10-27 16:59:22.700', '2004-05-24 08:10:00.150')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (23, 23, 23, '2024-03-11 17:13:45.970', '2018-06-04 14:10:16.260')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (24, 24, 24, '1977-04-07 12:16:55.930', '1983-06-04 04:13:13.090')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (25, 25, 25, '2013-10-24 15:41:08.380', '1985-06-28 03:35:15.100')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (26, 26, 26, '1974-10-02 07:31:29.370', '1986-03-11 18:54:43.590')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (27, 27, 27, '2017-10-25 19:08:44.210', '1998-04-01 09:50:46.970')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (28, 28, 28, '1996-04-06 20:25:06.940', '2005-05-04 03:14:15.530')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (29, 29, 29, '2005-11-04 05:34:08.750', '2015-12-04 06:33:13.080')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (30, 30, 30, '1997-03-17 10:29:07.250', '1990-05-04 15:49:59.160')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (31, 31, 31, '1991-12-13 13:30:37.220', '2008-06-27 17:30:09.500')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (32, 32, 32, '1986-08-01 03:25:12.060', '1997-04-09 09:36:45.200')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (33, 33, 33, '1978-12-24 13:11:35.650', '2016-09-14 20:00:07.890')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (34, 34, 34, '1977-10-16 20:14:24.310', '1976-11-29 14:08:14.470')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (35, 35, 35, '2016-11-19 08:33:01.680', '2011-01-25 21:50:06.790')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (36, 36, 36, '2004-01-24 02:13:26.740', '1999-07-25 04:16:15.820')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (37, 37, 37, '1973-05-02 19:25:42.720', '1987-03-04 01:06:46.470')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (38, 38, 38, '1976-08-17 12:58:28.430', '2000-06-28 01:57:29.050')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (39, 39, 39, '2003-11-30 05:46:32.140', '2001-05-05 07:23:44.480')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (40, 40, 40, '1973-01-11 07:49:43.340', '1975-09-13 10:43:15.990')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (41, 41, 41, '2024-04-19 02:41:47.020', '2007-06-20 20:55:09.570')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (42, 42, 42, '2023-08-29 20:38:18.080', '1971-02-21 14:38:10.090')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (43, 43, 43, '1974-11-08 04:39:19.340', '2015-04-11 02:44:38.560')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (44, 44, 44, '1970-03-04 03:30:54.660', '2021-06-01 08:50:40.030')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (45, 45, 45, '2004-02-06 17:46:03.110', '1977-09-20 11:12:12.440')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (46, 46, 46, '1983-04-04 17:35:33.620', '2022-10-31 10:10:41.780')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (47, 47, 47, '2013-11-25 09:15:09.910', '2001-11-28 11:46:55.560')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (48, 48, 48, '1997-09-01 13:43:40.060', '1985-01-29 08:53:17.710')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (49, 49, 49, '2008-04-10 03:00:06.030', '1991-08-13 14:26:57.210')
INSERT dbo.ATTENDANCE(attendantId, atendeeId, eventId, entryTime, exitTime) VALUES (50, 50, 50, '2017-08-13 14:26:11.910', '1982-02-21 01:43:58.190')
GO

--
-- Inserting data into table dbo.STAFF
--
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (1, 47552, 'K1')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (2, 28342, '8')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (3, 24287, 'TQ71')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (4, 43554, 'R127Y')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (5, 47553, '96A')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (6, 32343, '0C')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (7, 41280, 'ED6HJ1Y5ZP94J8170ZA9')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (8, 20287, '9Q9')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (9, 13281, '5H018F')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (10, 37280, '6P2')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (11, 24288, '99EJAZ')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (12, 28343, 'W8T8')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (13, 43555, '9Z76')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (14, 32344, '80V79')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (15, 17282, 'DT')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (16, 47554, '3T6IDJVR')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (17, 20288, '6K5VIT')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (18, 28344, 'G9172L90A1UZJ')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (19, 13282, '72')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (20, 17283, '5')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (21, 32345, '4')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (22, 24289, 'E9H52')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (23, 43556, '83S4PV')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (24, 47555, '20VB167A02')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (25, 28345, '12')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (26, 43557, 'L8S2ZJ')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (27, 20289, 'V97')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (28, 41281, '7720')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (29, 37281, '3')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (30, 32346, 'HIT6')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (31, 13283, '6UV8I2RZ9E9')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (32, 28346, '4J4G')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (33, 47556, '26')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (34, 24290, 'SAW')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (35, 17284, '51D256LF2G6')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (36, 41282, '75R1')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (37, 32347, 'S9KU960HM320D2B3VKHR')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (38, 20290, '095')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (39, 28347, 'H')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (40, 43558, 'J7')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (41, 32348, '39')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (42, 37282, 'YH92SQ')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (43, 47557, 'RM')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (44, 41283, '9TVEDD06N90')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (45, 24291, '87X')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (46, 28348, 'N')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (47, 13284, '5N')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (48, 43559, 'M3Y4')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (49, 32349, '28CHL0')
INSERT dbo.STAFF(memberId, enrollment, pass) VALUES (50, 17285, 'AGL75L99V36')
GO

--
-- Set NOEXEC to off
--

SET NOEXEC OFF
GO