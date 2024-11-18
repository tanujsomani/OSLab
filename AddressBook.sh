#!/bin/bash
# Address book file
ADDRESS_BOOK="address_book.txt"
# Create address book
create_address_book() {
    touch "$ADDRESS_BOOK"
    echo "Address book created."
}

# View address book
view_address_book() {
    if [[ -s "$ADDRESS_BOOK" ]]; then
        cat "$ADDRESS_BOOK"
    else
        echo "Address book is empty."
    fi
}

# Insert a record
insert_record() {
    echo "Enter name:"
    read name
    echo "Enter phone number:"
    read phone
    echo "Enter address:"
    read address
    echo "$name | $phone | $address" >> "$ADDRESS_BOOK"
    echo "Record inserted."
}

# Delete a record
delete_record() {
    echo "Enter name of the record to delete:"
    read name
    grep -v "^$name |" "$ADDRESS_BOOK" > "${ADDRESS_BOOK}.tmp"
    mv "${ADDRESS_BOOK}.tmp" "$ADDRESS_BOOK"
    echo "Record deleted."
}

# Modify a record
modify_record() {
    echo "Enter name of the record to modify:"
    read name
    echo "Enter new phone number:"
    read phone
    echo "Enter new address:"
    read address
    grep -v "^$name |" "$ADDRESS_BOOK" > "${ADDRESS_BOOK}.tmp"
    echo "$name | $phone | $address" >> "${ADDRESS_BOOK}.tmp"
    mv "${ADDRESS_BOOK}.tmp" "$ADDRESS_BOOK"
    echo "Record modified."
}
# Main menu
while true; do
    echo "1) Create address book"
    echo "2) View address book"
    echo "3) Insert a record"
    echo "4) Delete a record"
    echo "5) Modify a record"
    echo "6) Exit"
    read -p "Choose an option [1-6]: " option
    case $option in
        1) create_address_book ;;
        2) view_address_book ;;
        3) insert_record ;;
        4) delete_record ;;
        5) modify_record ;;
        6) exit 0 ;;
        *) echo "Invalid option, please try again." ;;
    esac
done
