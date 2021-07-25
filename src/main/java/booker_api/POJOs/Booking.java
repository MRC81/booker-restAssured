package booker_api.POJOs;


public class Booking {
    private String firstname, lastname, additionalneeds;
    private int totalprice;
    private boolean depositpaid;
    BookingDates bookingdates;

    public Booking(String firstname,
                   String lastname,
                   String additionalneeds,
                   int totalprice,
                   boolean depositpaid,
                   BookingDates bookingdates) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.additionalneeds = additionalneeds;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
    }

    // Getter Methods
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public boolean getDepositpaid() {
        return depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    // Setter Methods
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }


    /** This methods returns Booking object content in the String form
     *
     * @return String with Booking object field content
     */
    public String BookingToString() {
        return "First name: " + this.firstname + ", " +
                "\nLast name: " + this.lastname + ", " +
                "\nTotal price: " + this.totalprice + ", " +
                "\nDeposit paid: " + this.depositpaid + ", " +
                "\nBooking dates: " + this.bookingdates.getCheckin() + " - " + this.bookingdates.getCheckout() + ", " +
                "\nAdditional needs: " + this.additionalneeds;
    }

}
