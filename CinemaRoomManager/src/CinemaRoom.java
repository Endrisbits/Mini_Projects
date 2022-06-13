public class CinemaRoom {
    private
    enum seatsState {S, B}
    ; //S := free seat, B := booked
    int m_smallRoomLimit = 60; //Max seats for small room.
    int m_smallRoomPrice = 10; //Prices for big rooms
    int m_frontHalfPrice = 10; //Front half is more costly in bigger rooms
    int m_backHalfPrice = 8;
    int m_totalBookedTickets = 0;
    int m_rows;
    int m_seatsPerRow;
    boolean isSmallRoom;
    seatsState[][] seatsMatrix;

    void populateSeatsMatrix () {
        for (int i = 0; i < m_rows; i++) {
            for (int j = 0; j < m_seatsPerRow; j++) {
                seatsMatrix[i][j] = seatsState.S;
            }
        }
    }

    void printHeader ( int rows){
        System.out.printf("%nCinema:%n");
        System.out.print("  ");
        for (int i = 1; i <= m_seatsPerRow; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();
    }

    void printRoomLayout () {
        printHeader(m_rows);
        for (int i = 0; i < m_rows; i++) {
            System.out.printf("%d ", i + 1);
            for (seatsState s : seatsMatrix[i]) System.out.printf("%s ", s.name());
            System.out.println();
        }
        System.out.println();
    }

    public

    CinemaRoom(int rows, int seatsPerRow){
        m_rows = rows;
        m_seatsPerRow = seatsPerRow;
        this.seatsMatrix = new seatsState[rows][seatsPerRow];
        this.isSmallRoom = m_rows * m_seatsPerRow < m_smallRoomLimit;
        populateSeatsMatrix();
    }

    int getTicketPrice(int i) {
        if(isSmallRoom) return m_smallRoomPrice;
        else { //Big room. Now price depends on row position
            if(i < m_rows /2) return m_frontHalfPrice;
            else return m_backHalfPrice;
        }
    }

    boolean bookSeat(int i, int j) {
        if(i >= m_rows || i < 0 || j >= m_seatsPerRow || j < 0) {
            System.out.println("Wrong input!");
            return false;
        }
        else if(seatsMatrix[i][j] == seatsState.S) {
            seatsMatrix[i][j] = seatsState.B;
            m_totalBookedTickets++;
            System.out.printf("Booked! Ticket Price: $%d%n", getTicketPrice(i));
            return true;
        }
        else {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
    }

    void printStatistics() {
        System.out.printf("%nNumber of purchased tickets: %d", getTotalBookedTickets());
        float percBooked = (float) (100 * getTotalBookedTickets()*1.0)/(m_rows*m_seatsPerRow);
        System.out.printf("%nPercentage: %.2f%%", percBooked, m_rows*m_seatsPerRow);
        System.out.printf("%nCurrent income: $%d", calculateCurrentIncome());
        System.out.printf("%nTotal income: $%d", calculateTotalProfit());
    }

    private int calculateCurrentIncome() {
        int currentIncome = 0;
        for(int i=0; i<m_rows; i++){
            for(int j=0; j<m_seatsPerRow; j++) {
                if(seatsMatrix[i][j]==seatsState.B) currentIncome+=getTicketPrice(i);
            }
        }
        return currentIncome;
    }

    private int getTotalBookedTickets() {
        return m_totalBookedTickets;
    }

    public int calculateTotalProfit() {
        int totalSeats = m_rows * m_seatsPerRow;
        if (totalSeats <= m_smallRoomLimit){
            return totalSeats * m_smallRoomPrice;
        }
        else {
            int frontHalfRows = m_rows/2;
            int backHalfRows = m_rows-frontHalfRows;
            return (frontHalfRows * m_seatsPerRow * m_frontHalfPrice) + (backHalfRows * m_seatsPerRow * m_backHalfPrice);
        }
    }
}

