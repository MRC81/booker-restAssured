package booker_api;


public class BookingPOJO {

    private String firstname, lastname, additionalneeds;
    private int totalprice;
    private boolean depositpaid;
    Bookingdates bookingdates;

    public BookingPOJO(String firstname,
                String lastname,
                String additionalneeds,
                int totalprice,
                boolean depositpaid,
                Bookingdates bookingdates) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.additionalneeds = additionalneeds;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
    }

    public String printPOJO() {
        return "First name: " + this.firstname + ", " +
                "\nLast name: " + this.lastname + ", " +
                "\nTotal price: " + this.totalprice + ", " +
                "\nDeposit paid: " + this.depositpaid + ", " +
                "\nBooking dates: " + this.bookingdates.checkin + " - " + this.bookingdates.checkout + ", " +
                "\nAdditional needs: " + this.additionalneeds;
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

    public Bookingdates getBookingdates() {
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

    public void setBookingdates(Bookingdates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }


    public static class Bookingdates {
        private String checkin;
        private String checkout;

        public Bookingdates(String checkin, String checkout) {
            this.checkin = checkin;
            this.checkout = checkout;
        }

        // Getter Methods
        public String getCheckin() {
            return checkin;
        }

        public String getCheckout() {
            return checkout;
        }

        // Setter Methods
        public void setCheckin(String checkin) {
            this.checkin = checkin;
        }

        public void setCheckout(String checkout) {
            this.checkout = checkout;
        }
    }
}
