# K2534816 Bus Ticket Booking Counter System

Java Swing GUI application for a private travel company's counter staff to create, update, delete, search, and print intercity bus ticket bookings.

## Features

- Create one-way and return bookings.
- Select destination, bus type, travel date, and travel type.
- Automatic fare calculation with booking fee, return discount, tax/VAT, and total.
- Prevent duplicate booking for the same NIC/passport, destination, and travel date.
- Update and delete existing bookings.
- Search by NIC/passport and filter by destination.
- Print formatted ticket details.
- Save and load bookings from `data/bookings.csv`.

## Requirements

- Java 8 or later installed.

## Run

Compile:

```bash
javac -source 8 -target 8 -d out src/*.java
```

Run:

```bash
java -cp out K2534816Main
```

## Storage

Bookings are stored in CSV format at:

```text
data/bookings.csv
```
