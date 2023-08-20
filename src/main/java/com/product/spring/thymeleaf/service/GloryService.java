package com.product.spring.thymeleaf.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.product.spring.thymeleaf.entity.Company;
import com.product.spring.thymeleaf.entity.Customer;
import com.product.spring.thymeleaf.entity.ProductDetails;
import com.product.spring.thymeleaf.repository.CompanyRepository;
import com.product.spring.thymeleaf.repository.CustomerInfoRepository;
import com.product.spring.thymeleaf.repository.ImageGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GloryService {

    @Autowired
    private ImageGalleryRepository fileRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @SuppressWarnings({"rawtypes", "unused"})
    public byte[] getGloryReport(String cusName,String orderDate,Integer advance,Integer gst,String orderReceiver, HttpServletResponse response) {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date order_date = sdf.parse(orderDate);
            Optional<Customer> cusData = customerInfoRepository.findByCustomerNameAndOrderDate(cusName,order_date);
            if(cusData.isPresent()){
                Customer dto = cusData.get();
                dto.setAdvance(advance);
                dto.setGst(gst);
                customerInfoRepository.save(dto);
            }
            Optional<Company> comData = companyRepository.findByCompanyname("Glory Hotel Ware");
            List<ProductDetails> list = fileRepository.findByCustomerNameAndOrderDate(cusName,order_date);
            if(cusData.isPresent() && comData.isPresent() && list.size() > 0){
                int n = 0;
                Image img = null;


                Date date = sdf.parse(sdf.format(new Date()));
                String currentdate = new SimpleDateFormat("dd/MM/yyyy").format(date);

                Document document = new Document(PageSize.A4, -20, -20, 40, 40);
               // document.setMargins(1, 1, 40, 40);
                PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
               // document.setMargins(20, 20, 30, 30);
                document.open();
                document.addCreationDate();
                Image image = null;

                BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
                Font font1 = new Font(bf, 10, Font.BOLD);
                Font font2 = new Font(bf, 15, Font.BOLD);
                Font font3 = new Font(bf, 8, Font.BOLD);
                Font font = new Font(bf, 10, Font.NORMAL);

                PdfPTable table11 = new PdfPTable(20);
                PdfPTable table12 = new PdfPTable(30);
                PdfPTable img1 = new PdfPTable(25);
                PdfPTable img2 = new PdfPTable(25);
                PdfPTable img3 = new PdfPTable(5);
                PdfPTable img4 = new PdfPTable(20);

                PdfPTable masterTable = new PdfPTable(50);

                PdfPCell cell;

                cell = new PdfPCell(new Phrase("Order DATE :"+orderDate, font1));
                cell.setBorder(0);
                cell.setColspan(25);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img1.addCell(cell);
                cell = new PdfPCell(new Phrase("Order Delivery DATE :"+currentdate, font1));
                cell.setBorder(0);
                cell.setColspan(25);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img1.addCell(cell);
                cell = new PdfPCell(new Phrase("Order Receiver Name :"+orderReceiver, font1));
                cell.setBorder(0);
                cell.setColspan(25);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img1.addCell(cell);

                Image logo = null;
                logo = Image.getInstance(comData.get().getLogo());
                logo.scaleAbsoluteHeight(50);
                logo.scaleAbsoluteWidth(50);


                cell = new PdfPCell(logo);
                cell.setBorder(0);
                cell.setColspan(5);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                img3.addCell(cell);

                cell = new PdfPCell(new Phrase(comData.get().getCompanyname(), font2));
                cell.setBorder(0);
                cell.setColspan(20);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img4.addCell(cell);

                cell = new PdfPCell(new Phrase(comData.get().getCompanyaddress1(), font3));
                cell.setBorder(0);
                cell.setColspan(20);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img4.addCell(cell);
                cell = new PdfPCell(new Phrase(comData.get().getCompanyaddress2() == null ? "":comData.get().getCompanyaddress2(), font3));
                cell.setBorder(0);
                cell.setColspan(20);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img4.addCell(cell);
                cell = new PdfPCell(new Phrase("  G.S.T No :- 07AZHPA2050LIZM", font3));
                cell.setBorder(0);
                cell.setColspan(20);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                img4.addCell(cell);


                cell = new PdfPCell(img3);
                cell.setColspan(5);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                img2.addCell(cell);

                cell = new PdfPCell(img4);
                cell.setColspan(20);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                img2.addCell(cell);

                cell = new PdfPCell(img1);
                cell.setColspan(25);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(img2);
                cell.setColspan(25);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setBorder(0);
                cell.setColspan(50);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(comData.get().getShowroomaddress(), font));
                cell.setBorder(0);
                cell.setColspan(50);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("E-mail : gloryhotelware.9211@gmail.com , Ph - 9211878760 , 9560082186", font));
                cell.setBorder(0);
                cell.setColspan(50);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(""));
                cell.setColspan(50);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                masterTable.setHeaderRows(1);
                masterTable.addCell(cell);

                int a = 0;


                cell = new PdfPCell(new Phrase("Bill To", font3));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(2);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Name :", font3));
                cell.setColspan(5);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(cusData.get().getCustomerName(), font));
                cell.setColspan(14);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Mo.No :", font3));
                cell.setColspan(6);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(cusData.get().getMobile(), font));
                cell.setColspan(15);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(8);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(2);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Add   :", font3));
                cell.setColspan(5);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(cusData.get().getAddress(), font));
                cell.setColspan(14);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Customer Bank Details :", font3));
                cell.setColspan(6);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(cusData.get().getCustomerBank(), font));
                cell.setColspan(15);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(8);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);


                //--------------------- table ---------------------------

                cell = new PdfPCell(new Phrase("S.No.", font1));
                cell.setColspan(3);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Item", font1));
                cell.setColspan(8);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Description", font1));
                cell.setColspan(12);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("price", font1));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Packing charge", font1));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Packing charge Total", font1));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Qty", font1));
                cell.setColspan(3);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);
                cell = new PdfPCell(new Phrase("Amount", font1));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) .1);
                cell.setBorderWidthLeft((float) 0);

                masterTable.addCell(cell);
                a = a + 1;
                int total_amount =0;
                int pakageChargeSum =0;
                for (int i = 0; i < list.size(); i++) {
                    if(i == 9|| i == 18 || i == 27){
                        document.add(masterTable);
                        document.newPage();
                        masterTable = new PdfPTable(50);
                    }
                    ProductDetails dto = list.get(i);
                    cell = new PdfPCell(new Phrase("" + a++, font));
                    cell.setColspan(3);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    masterTable.addCell(cell);

                    img = Image.getInstance(dto.getImage());
                    img.scaleAbsoluteHeight(50);
                    img.scaleAbsoluteWidth(80);

                    cell = new PdfPCell(img);
                    cell.setColspan(8);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(dto.getDescription(), font));
                    cell.setColspan(12);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(dto.getPrice()), font));
                    cell.setColspan(6);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(dto.getPackageCharge()), font));
                    cell.setColspan(6);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);
                   int pakageChargeTotal = dto.getPackageCharge() * dto.getQty();
                    pakageChargeSum = pakageChargeSum + pakageChargeTotal;
                    cell = new PdfPCell(new Phrase(String.valueOf(pakageChargeTotal), font));
                    cell.setColspan(6);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(dto.getQty()), font));
                    cell.setColspan(3);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);
                    int amount = dto.getPrice() * dto.getQty();
                    total_amount =amount+total_amount;
                    cell = new PdfPCell(new Phrase(String.valueOf(amount), font));
                    cell.setColspan(6);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop((float) 0);
                    cell.setBorderWidthLeft((float) 0);
                    masterTable.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("Amount", font));
                cell.setColspan(44);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) .1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(total_amount), font));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Packing Charge", font));
                cell.setColspan(44);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) .1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(pakageChargeSum), font));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);


                cell = new PdfPCell(new Phrase("G.S.T 18%", font));
                cell.setColspan(44);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) .1);
                masterTable.addCell(cell);
                int gst_amount = cusData.get().getGst();

                cell = new PdfPCell(new Phrase(String.valueOf(gst), font));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Total Amount", font));
                cell.setColspan(44);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) .1);
                masterTable.addCell(cell);

                int totalamount_A_P_G = total_amount+pakageChargeSum+gst;
                cell = new PdfPCell(new Phrase(String.valueOf(totalamount_A_P_G), font));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);


                cell = new PdfPCell(new Phrase("Advance Amount", font));
                cell.setColspan(44);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) .1);
                masterTable.addCell(cell);
                int advance_amount = advance;
                cell = new PdfPCell(new Phrase(String.valueOf(advance_amount), font));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Balance Amount", font1));
                cell.setColspan(44);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) .1);
                masterTable.addCell(cell);

                int balance_amount = totalamount_A_P_G - advance_amount;

                cell = new PdfPCell(new Phrase(String.valueOf(balance_amount), font1));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop((float) 0);
                cell.setBorderWidthLeft((float) 0);
                masterTable.addCell(cell);

                //---------------------------------bank detail------------

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Kindly find below our Glory HotelWare account details", font1));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Account No :-" +comData.get().getCompanyaccount(), font));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);
                cell = new PdfPCell(new Phrase("Bank Name :-" +comData.get().getBankname(), font));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("IFSC Code :-" +comData.get().getIfsccode(), font));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Account Holder Name :-" +comData.get().getAccholdername(), font));
                cell.setColspan(50);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                masterTable.addCell(cell);


                document.open();
                document.add(masterTable);
                document.close();
                pdfWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bstream.toByteArray();
    }
}
