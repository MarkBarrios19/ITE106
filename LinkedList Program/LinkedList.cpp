#include <iostream>
using namespace std;

struct Node {
    int data;       
    Node* link;     

    Node(int value) : data(value), link(nullptr) {}
};

class LinkedList {
private:
    Node* head;        
    int counter;    

public:
    LinkedList();
    ~LinkedList();
    void append(int num);
    void display() const;
    int getCount() const;
    void search(int num) const;
    void remove(int num);
    void reverse();
};

LinkedList::LinkedList() : head(nullptr), counter(0) {}

void LinkedList::append(int num) {
    Node* temp = new Node(num);
    if (head == nullptr) {
        head = temp; 
    } else {
        Node* current = head; 
        while (current->link != nullptr) {
            current = current->link;
        }
        current->link = temp; 
    }
    counter++; 
    cout << "Inserted: " << num << endl;
}

void LinkedList::display() const {
    Node* current = head; 
    if (!current) {
        cout << "The list is empty." << endl;
        return;
    }
    cout << "Linked List: ";
    while (current != nullptr) {
        cout << current->data << " ";
        current = current->link;
    }
    cout << endl;
}

int LinkedList::getCount() const {
    return counter;
}

void LinkedList::search(int num) const {
    Node* current = head;
    while (current != nullptr) {
        if (current->data == num) {
            cout << "Node with value " << num << " found." << endl;
            return;
        }
        current = current->link;
    }
    cout << "Node with value " << num << " not found." << endl;
}

void LinkedList::remove(int num) {
    Node* current = head;
    Node* prev = nullptr;
    while (current != nullptr && current->data != num) {
        prev = current;
        current = current->link;
    }
    if (current == nullptr) {
        cout << "Node with value " << num << " not found." << endl;
        return;
    }
    if (prev == nullptr) {
        head = current->link; 
    } else {
        prev->link = current->link; 
    }
    delete current;
    counter--;
    cout << "Node with value " << num << " removed." << endl;
}

LinkedList::~LinkedList() {
    Node* current = head;
    while (current != nullptr) {
        Node* temp = current->link;
        delete current;
        current = temp;
    }
}

void LinkedList::reverse() {
    Node* prev = nullptr;
    Node* current = head;
    Node* next = nullptr;
    
    while (current != nullptr) {
        next = current->link;
        current->link = prev;
        prev = current;
        current = next;
    }
    head = prev;
    cout << "List reversed." << endl;
}

int main() {
    LinkedList list;
    int num;

    cout << "Enter numbers to add to the list (enter 0 to stop):" << endl;
    while (true) {
        cin >> num;
        if (num == 0) break; 
        list.append(num);
    }

    list.display();
    cout << "Number of nodes: " << list.getCount() << endl;

    cout << "Enter a number to search: ";
    cin >> num;
    list.search(num);
    
    cout << "Enter a number to remove: ";
    cin >> num;
    list.remove(num);
    list.display();

    list.reverse();
    list.display();

    return 0;
}