
use dryhomecrm;
CREATE TABLE [dbo].[products](
  [prod_id] [int] IDENTITY(1,1) NOT NULL,
  [prod_name] [varchar](50) NULL,
  [prod_description] [varchar](200) NULL
    CONSTRAINT [PK_products_1] PRIMARY KEY CLUSTERED
      (
        [prod_id] ASC
      )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);

CREATE TABLE [dbo].[lookup](
  [id] [int] IDENTITY(1,1) NOT NULL,
  [used] [varchar](50) NULL,
  [lu_id] [int] NULL,
  [Descript] [varchar](50) NULL,
  [order_no] [int] NULL,
  CONSTRAINT [PK_lookup_1] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);

CREATE TABLE [dbo].[Damp_Proofers](
  [ID] [int] IDENTITY(1,1) NOT NULL,
  [Company Name:] [nvarchar](255) NULL,
  [Address 1] [nvarchar](255) NULL,
  [Address 2] [nvarchar](255) NULL,
  [Address3] [nvarchar](255) NULL,
  [Town] [nvarchar](255) NULL,
  [Post Code] [nvarchar](13) NULL,
  [Tel no] [nvarchar](12) NULL,
  [Title] [nvarchar](255) NULL,
  [First] [nvarchar](15) NULL,
  [Surname] [nvarchar](15) NULL,
  [Mobile] [nvarchar](12) NULL,
  [Products] [nvarchar](255) NULL,
  [Interested_OLD] [nvarchar](255) NULL,
  [Notes] [nvarchar](max) NULL,
  [Paid] [money] NULL,
  [Interested] [int] NULL,
  CONSTRAINT [aaaaaDamp Proofers_PK] PRIMARY KEY NONCLUSTERED
    (
      [ID] ASC
    )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);

CREATE TABLE [dbo].[domestics](
  [ID] [int] IDENTITY(1,1) NOT NULL,
  [Address 1] [nvarchar](255) NULL,
  [Address 2] [nvarchar](255) NULL,
  [Address3] [nvarchar](255) NULL,
  [Town] [nvarchar](255) NULL,
  [Post Code] [nvarchar](13) NULL,
  [Tel no] [nvarchar](12) NULL,
  [Title] [nvarchar](255) NULL,
  [First] [nvarchar](15) NULL,
  [Surname] [nvarchar](15) NULL,
  [Mobile] [nvarchar](12) NULL,
  [Products] [nvarchar](255) NULL,
  [Interested_OLD] [nvarchar](255) NULL,
  [Notes] [nvarchar](max) NULL,
  [Paid] [money] NULL,
  [lead] [char](1) NULL,
  [lead_name] [nvarchar](255) NULL,
  [Interested] [int] NULL,
  [lead_number] [nvarchar](50) NULL,
  [lead_mob] [nvarchar](50) NULL,
  [sale] [char](1) NULL,
  [property_type] [nvarchar](50) NULL,
  [unit_pq] [nvarchar](255) NULL,
  [inst_pq] [nvarchar](255) NULL,
  [inv_date] [nvarchar](50) NULL,
  [inv_number] [nvarchar](50) NULL,
  CONSTRAINT [domestic_PK] PRIMARY KEY NONCLUSTERED
    (
      [ID] ASC
    )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);


CREATE TABLE [dbo].[dp_orders](
  [order_id] [int] IDENTITY(1,1) NOT NULL,
  [order_number] [varchar](10) NULL,
  [company_id] [int] NULL,
  [Date] [datetime] NULL,
  [Notes1] [varchar](100) NULL,
  [Status] [int] NULL,
  [despatch_date] [datetime] NULL,
  [invoice_date] [datetime] NULL,
  [payment_date] [datetime] NULL,
  [invAddressId] [int] NULL,
  [delAddressId] [int] NULL,
  [vatRate] [decimal](18, 2) NULL,
  [notes2] [varchar](100) NULL,
  [internal_notes] [varchar](500) NULL,
  [invoice_number] [nchar](10) NULL,
  [invContactId] [int] NULL,
  [delContactId] [int] NULL,
  [payment_status] [varchar](100) NULL,
  [payment_type] [varchar](100) NULL,
  [payment_amount] [decimal](18, 2) NULL,
  [placed_by] [varchar](100) NULL,
  [method] [int] NULL,
  CONSTRAINT [PK_orders_1] PRIMARY KEY CLUSTERED
    (
      [order_id] ASC
    )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);

CREATE TABLE [dbo].[dp_order_items](
  [id] [int] IDENTITY(1,1) NOT NULL,
  [order_id] [int] NULL,
  [Prod_id] [int] NULL,
  [price] [decimal](18, 2) NULL,
  [qty] [int] NULL CONSTRAINT [DF_order_items_qty]  DEFAULT ((1)),
  [Notes] [varchar](200) NULL,
  [order] [int] NULL,
  [serial_number] [varchar](500) NULL,
  CONSTRAINT [PK_order_items] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);


CREATE TABLE [dbo].[dp_contacts](
  [id] [int] IDENTITY(1,1) NOT NULL,
  [company_id] [int] NOT NULL,
  [title] [nvarchar](50) NULL,
  [first] [nvarchar](50) NULL,
  [surname] [nvarchar](50) NULL
);


CREATE TABLE [dbo].[dp_addresses](
  [id] [int] IDENTITY(1,1) NOT NULL,
  [company_id] [int] NOT NULL,
  [company_name] [varchar](50) NULL,
  [address1] [varchar](50) NULL,
  [address2] [varchar](50) NULL,
  [address3] [varchar](50) NULL,
  [town] [varchar](50) NULL,
  [post_code] [varchar](50) NULL
);


CREATE TABLE [dbo].[dom_orders](
[order_id] [int] IDENTITY(1,1) NOT NULL,
[order_number] [varchar](10) NULL,
[dom_id] [int] NULL,
[Date] [datetime] NULL,
[Notes1] [varchar](100) NULL,
[Status] [int] NULL,
[despatch_date] [datetime] NULL,
[invoice_date] [datetime] NULL,
[payment_date] [datetime] NULL,
[invAddressId] [int] NULL,
[delAddressId] [int] NULL,
[vatRate] [decimal](18, 2) NULL,
[notes2] [varchar](100) NULL,
[internal_notes] [varchar](500) NULL,
[invoice_number] [nchar](10) NULL,
[invContactId] [int] NULL,
[delContactId] [int] NULL,
[payment_status] [varchar](100) NULL,
[payment_type] [varchar](100) NULL,
[payment_amount] [decimal](18, 2) NULL,
[placed_by] [varchar](100) NULL,
[method] [int] NULL,
CONSTRAINT [PK_domOrders_1] PRIMARY KEY CLUSTERED
(
[order_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);

CREATE TABLE [dbo].[dom_order_items](
[id] [int] IDENTITY(1,1) NOT NULL,
[dom_order_id] [int] NULL,
[Prod_id] [int] NULL,
[price] [decimal](18, 2) NULL,
[qty] [int] NULL,
[Notes] [varchar](200) NULL,
[order] [int] NULL,
[serial_number] [varchar](500) NULL,
CONSTRAINT [PK_dom_order_items] PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
);
