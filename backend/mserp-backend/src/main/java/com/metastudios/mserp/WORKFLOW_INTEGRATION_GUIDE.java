/**
 * Business Workflow Integration Guide - MetaStudios ERP
 * 
 * This document describes the integrated workflows between modules.
 * 
 * ============================================================================
 * WORKFLOW 1: SALES ORDER TO INVOICE WORKFLOW
 * ============================================================================
 * 
 * Flow:
 *   1. Create SalesOrder via POST /api/sales-orders
 *   2. Update SalesOrder status to "FULFILLED" via fulfillSalesOrder() method
 *   3. Create Invoice linked to SalesOrder via POST /api/invoices
 *      - Include salesOrderId in request
 *      - Invoice will reference the SalesOrder
 *   4. Track workflow progress in Dashboard
 * 
 * Key Methods:
 *   - SalesOrderService.fulfillSalesOrder(Long id) - marks order as fulfilled
 *   - InvoiceService.createInvoice(InvoiceRequestDTO) - creates linked invoice
 *   - DashboardService.getDashboardData() - shows totalRevenue metrics
 * 
 * Affected Tables:
 *   - sales_orders (status updated)
 *   - invoices (new record linked to sales_order)
 *   - dashboard metrics (totalRevenue calculated from invoices)
 * 
 * ============================================================================
 * WORKFLOW 2: INVOICE TO PAYMENT WORKFLOW
 * ============================================================================
 * 
 * Flow:
 *   1. Invoice is created (See Workflow 1)
 *   2. Update Invoice status to "DUE" or "OVERDUE"
 *   3. Create Payment linked to Invoice via POST /api/payments
 *      - Include invoiceId in request
 *   4. Update Payment status to "COMPLETED" when payment is received
 *   5. Dashboard tracks totalPendingPayments metric
 * 
 * Key Methods:
 *   - InvoiceService.createInvoice(InvoiceRequestDTO) - creates invoice
 *   - PaymentService.createPayment(PaymentRequestDTO) - records payment
 *   - DashboardService.getDashboardData() - shows totalPendingPayments
 * 
 * Affected Tables:
 *   - invoices (status updated to track payment status)
 *   - payments (new records linked to invoices)
 *   - dashboard metrics (totalPendingPayments, totalRevenue)
 * 
 * ============================================================================
 * WORKFLOW 3: PURCHASE ORDER TO INVENTORY WORKFLOW
 * ============================================================================
 * 
 * Flow:
 *   1. Create PurchaseOrder via POST /api/purchase-orders
 *   2. Update PurchaseOrder status to "COMPLETED" via completePurchaseOrder()
 *   3. Inventory stock should be updated automatically
 *      - Call InventoryService.increaseStock(productId, quantity)
 *   4. Update Inventory record lastUpdated field
 * 
 * Key Methods:
 *   - PurchaseOrderService.completePurchaseOrder(Long id) - marks PO complete
 *   - InventoryService.increaseStock(Long productId, Integer quantity) - adds stock
 *   - DashboardService.getDashboardData() - tracks total products in inventory
 * 
 * Affected Tables:
 *   - purchase_orders (status = "COMPLETED")
 *   - inventory (currentStock increased, lastUpdated updated)
 *   - products (implicit relation)
 * 
 * Note: Future implementation should include PurchaseOrderLineItem table
 *       to track individual products and quantities per PO.
 * 
 * ============================================================================
 * WORKFLOW 4: INVENTORY MANAGEMENT WORKFLOW
 * ============================================================================
 * 
 * Outbound Stock (Sales):
 *   - Call InventoryService.decreaseStock(productId, quantity)
 *   - Throws ResourceNotFoundException if insufficient stock
 *   - Updates lastUpdated timestamp
 * 
 * Inbound Stock (Purchases):
 *   - Call InventoryService.increaseStock(productId, quantity)
 *   - Updates currentStock and lastUpdated timestamp
 * 
 * Stock Alerts:
 *   - Monitor currentStock vs minimumStock
 *   - Monitor currentStock vs maximumStock
 *   - Dashboard should alert when stock is below minimum
 * 
 * Key Methods:
 *   - InventoryService.increaseStock(Long productId, Integer quantityAdded)
 *   - InventoryService.decreaseStock(Long productId, Integer quantityRemoved)
 * 
 * ============================================================================
 * WORKFLOW 5: EXPENSE TRACKING & FINANCIAL DASHBOARD
 * ============================================================================
 * 
 * Expense Recording:
 *   1. Create Expense via POST /api/expenses
 *   2. Expense status: "PENDING", "APPROVED", "PAID", "REJECTED"
 *   3. Track expenses by category
 * 
 * Financial Metrics Calculated:
 *   - totalRevenue: Sum of invoice amounts where status = "PAID" or "COMPLETED"
 *   - totalExpenses: Sum of all expense amounts
 *   - totalPendingInvoices: Count of invoices with status = "PENDING"
 *   - totalPendingPayments: Count of payments with status = "PENDING"
 * 
 * Key Methods:
 *   - DashboardService.calculateTotalRevenue()
 *   - DashboardService.calculateTotalExpenses()
 *   - DashboardService.countPendingInvoices()
 *   - DashboardService.countPendingPayments()
 * 
 * Affected Endpoints:
 *   - GET /api/dashboard (returns all metrics)
 * 
 * ============================================================================
 * DASHBOARD METRICS (Updated)
 * ============================================================================
 * 
 * Operational Metrics:
 *   - totalUsers: Count from users table
 *   - totalEmployees: Count from employees table
 *   - totalCustomers: Count from customers table
 *   - totalLeads: Count from leads table
 *   - totalProducts: Count from products table
 *   - totalAttendanceRecords: Count from attendance table
 *   - totalLeaveRequests: Count from leave_requests table
 * 
 * Financial Metrics:
 *   - totalRevenue: Sum of paid/completed invoices
 *   - totalExpenses: Sum of all expenses
 *   - totalPendingInvoices: Count of pending invoices
 *   - totalPendingPayments: Count of pending payments
 * 
 * ============================================================================
 * ENTITY RELATIONSHIPS
 * ============================================================================
 * 
 * SalesOrder
 *   ├── Customer (ManyToOne) - who the order is for
 *   └── Invoice (OneToMany) - orders can generate multiple invoices
 * 
 * Invoice
 *   ├── Customer (ManyToOne) - customer being invoiced
 *   ├── SalesOrder (ManyToOne) - linked sales order
 *   └── Payment (OneToMany) - multiple payments per invoice
 * 
 * Payment
 *   └── Invoice (ManyToOne) - payment for specific invoice
 * 
 * PurchaseOrder
 *   └── Vendor (ManyToOne) - vendor ordering from
 * 
 * Inventory
 *   └── Product (ManyToOne) - product being tracked
 * 
 * Expense
 *   └── (Standalone) - independent expense tracking
 * 
 * ============================================================================
 * STATUS VALUES
 * ============================================================================
 * 
 * SalesOrder:
 *   - NEW, PROCESSING, FULFILLED, COMPLETED, CANCELLED
 * 
 * Invoice:
 *   - PENDING, SENT, PAID, OVERDUE, CANCELLED
 * 
 * Payment:
 *   - PENDING, COMPLETED, FAILED, REFUNDED
 * 
 * PurchaseOrder:
 *   - PENDING, CONFIRMED, SHIPPED, COMPLETED, CANCELLED
 * 
 * Expense:
 *   - PENDING, APPROVED, PAID, REJECTED
 * 
 * ============================================================================
 * API ENDPOINTS SUMMARY
 * ============================================================================
 * 
 * Sales Module:
 *   POST   /api/sales-orders            - Create sales order
 *   GET    /api/sales-orders            - List all sales orders
 *   GET    /api/sales-orders/{id}       - Get sales order details
 *   DELETE /api/sales-orders/{id}       - Delete sales order
 * 
 * Invoice Module:
 *   POST   /api/invoices                - Create invoice (linked to sales order)
 *   GET    /api/invoices                - List all invoices
 *   GET    /api/invoices/{id}           - Get invoice details
 *   DELETE /api/invoices/{id}           - Delete invoice
 * 
 * Payment Module:
 *   POST   /api/payments                - Record payment
 *   GET    /api/payments                - List all payments
 *   GET    /api/payments/{id}           - Get payment details
 *   DELETE /api/payments/{id}           - Delete payment
 * 
 * Purchase Order Module:
 *   POST   /api/purchase-orders         - Create purchase order
 *   GET    /api/purchase-orders         - List all purchase orders
 *   GET    /api/purchase-orders/{id}    - Get purchase order details
 *   DELETE /api/purchase-orders/{id}    - Delete purchase order
 * 
 * Inventory Module:
 *   POST   /api/inventory               - Create inventory record
 *   GET    /api/inventory               - List all inventory
 *   GET    /api/inventory/{id}          - Get inventory details
 *   DELETE /api/inventory/{id}          - Delete inventory
 * 
 * Expense Module:
 *   POST   /api/expenses                - Create expense
 *   GET    /api/expenses                - List all expenses
 *   GET    /api/expenses/{id}           - Get expense details
 *   DELETE /api/expenses/{id}           - Delete expense
 * 
 * Vendor Module:
 *   POST   /api/vendors                 - Create vendor
 *   GET    /api/vendors                 - List all vendors
 *   GET    /api/vendors/{id}            - Get vendor details
 *   DELETE /api/vendors/{id}            - Delete vendor
 * 
 * Dashboard:
 *   GET    /api/dashboard               - Get all dashboard metrics
 * 
 * ============================================================================
 * FUTURE ENHANCEMENTS
 * ============================================================================
 * 
 * 1. PurchaseOrderLineItem entity - track products per PO with quantities
 * 2. SalesOrderLineItem entity - track products per sales order
 * 3. Tax calculations for invoices
 * 4. Discount handling for sales orders
 * 5. Recurring invoices/subscriptions
 * 6. Automated payment reminders
 * 7. Approval workflows for expenses and purchase orders
 * 8. Invoice templates
 * 9. Payment gateway integration
 * 10. Audit trail for all financial transactions
 * 
 * ============================================================================
 */
