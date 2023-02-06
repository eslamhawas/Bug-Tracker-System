Create database ProjectDB
on primary (
name =Project_main,
filename="E:\project.mdf",
size=16MB,
filegrowth=16MB,
maxsize=unlimited
)




log on (
name =Project_log,
filename="E:\project.ldf",
size=8MB,
filegrowth=16MB,
maxsize=unlimited
)



create table users(

role nvarchar(50) ,
username nvarchar(50) primary key,
password nvarchar(50) ,
firstName nvarchar(50),
lastName nvarchar(50),
Email nvarchar(50),
birthDate nvarchar(50),


)

create table bugs(

bug_title  nvarchar(50),
bug_type nvarchar(50),
priority nvarchar(50),
bug_level nvarchar(50),
project nvarchar(50) primary key,
status nvarchar(50),
bug_date nvarchar(50),
developer_assigned nvarchar(50) ,
reported_by nvarchar(50),
)

insert into users (role,username,password,firstName,lastName,Email,birthDate)
values ('Tester','mariemfaried','20210922','mariem','faried','mariem_faried@gmail.com','24/08/2003'),
	   ('ProjectManager','mariemwalid','20210930','mariem','walid','mariem_walid@gmail.com','02/11/2003'),
	   ('Developer','marwanmetwally','20210894','marwan','metwally','marwan_metwally@gmail.com','03/15/2004'),
	   ('Admin','mostafamohdy','20210937','mostafa','mohdy','mostafa_mohdy@gmail.com','03/06/2003'),
	   ('Admin','eslamhawas','20210156','eslam','hawas','eslam_hawas@gmail.com','24/01/2004')


create table email (

[for] nvarchar(50),
message nvarchar(50),
[from] nvarchar(50),

)


create table username(
username nvarchar(50)
)


create table ScreenShots(
projectName nvarchar(50)  references bugs(project),
ScreenShot image
)




