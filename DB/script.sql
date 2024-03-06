USE [ShoppingCart]
GO
/****** Object:  Table [dbo].[orderDetail]    Script Date: 3/6/2024 7:54:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orderDetail](
	[odId] [varchar](50) NOT NULL,
	[orderId] [varchar](45) NULL,
	[productId] [int] NULL,
	[quantity] [int] NULL,
	[amount] [decimal](10, 2) NULL,
 CONSTRAINT [PK_orderDetail] PRIMARY KEY CLUSTERED 
(
	[odId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 3/6/2024 7:54:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[orderid] [varchar](45) NOT NULL,
	[userId] [int] NULL,
	[amount] [decimal](10, 2) NULL,
	[status] [varchar](50) NULL,
 CONSTRAINT [PK_orders] PRIMARY KEY CLUSTERED 
(
	[orderid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 3/6/2024 7:54:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[pid] [int] IDENTITY(1,1) NOT NULL,
	[pname] [varchar](100) NOT NULL,
	[ptype] [varchar](20) NULL,
	[pinfo] [varchar](350) NULL,
	[pprice] [decimal](12, 2) NULL,
	[pquantity] [int] NULL,
	[image] [varchar](500) NULL,
	[status] [varchar](50) NULL,
	[sale] [decimal](2, 2) NULL,
 CONSTRAINT [PK_product] PRIMARY KEY CLUSTERED 
(
	[pid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[transactions]    Script Date: 3/6/2024 7:54:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[transactions](
	[transId] [int] IDENTITY(1,1) NOT NULL,
	[userId] [int] NULL,
	[time] [datetime] NOT NULL,
	[amount] [decimal](10, 2) NOT NULL,
	[status] [varbinary](20) NOT NULL,
	[orderId] [varchar](45) NOT NULL,
	[email] [varchar](60) NULL,
	[updateTime] [datetime] NOT NULL,
	[updateBy] [int] NOT NULL,
	[name] [nvarchar](50) NULL,
	[mobile] [varchar](10) NULL,
	[address] [nvarchar](100) NULL,
	[payment] [varchar](50) NULL,
 CONSTRAINT [PK_transactions] PRIMARY KEY CLUSTERED 
(
	[transId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user]    Script Date: 3/6/2024 7:54:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user](
	[userId] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](60) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[mobile] [bigint] NOT NULL,
	[address] [varchar](250) NOT NULL,
	[pincode] [int] NOT NULL,
	[password] [varchar](20) NOT NULL,
	[status] [varchar](20) NOT NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[userId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [email] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_demand]    Script Date: 3/6/2024 7:54:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_demand](
	[ussername] [varchar](60) NOT NULL,
	[prodid] [varchar](45) NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_user_demand] PRIMARY KEY CLUSTERED 
(
	[ussername] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[orderDetail]  WITH CHECK ADD  CONSTRAINT [FK_orderDetail_orders] FOREIGN KEY([orderId])
REFERENCES [dbo].[orders] ([orderid])
GO
ALTER TABLE [dbo].[orderDetail] CHECK CONSTRAINT [FK_orderDetail_orders]
GO
ALTER TABLE [dbo].[orderDetail]  WITH CHECK ADD  CONSTRAINT [FK_orderDetail_product1] FOREIGN KEY([productId])
REFERENCES [dbo].[product] ([pid])
GO
ALTER TABLE [dbo].[orderDetail] CHECK CONSTRAINT [FK_orderDetail_product1]
GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD  CONSTRAINT [FK_transactions_orders] FOREIGN KEY([orderId])
REFERENCES [dbo].[orders] ([orderid])
GO
ALTER TABLE [dbo].[transactions] CHECK CONSTRAINT [FK_transactions_orders]
GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD  CONSTRAINT [FK_transactions_user] FOREIGN KEY([userId])
REFERENCES [dbo].[user] ([userId])
GO
ALTER TABLE [dbo].[transactions] CHECK CONSTRAINT [FK_transactions_user]
GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD  CONSTRAINT [FK_transactions_user1] FOREIGN KEY([updateBy])
REFERENCES [dbo].[user] ([userId])
GO
ALTER TABLE [dbo].[transactions] CHECK CONSTRAINT [FK_transactions_user1]
GO
