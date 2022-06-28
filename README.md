# disys_final
Distributed Systems Semester Project


Our task is to build an application that generates an invoice PDF for a given customer. Use JavaFX to create the UI for the application.

- Use Java Spring Boot to create the REST-based API.
- Use ActiveMQ to manage the message queue.

The workflow is as follows:
• You can input a customer id into the UI and click “Generate Invoice”
• A HTTP Request calls the REST-based API
• The application starts a new data gathering job
• When the data is gathered, it gets send to the PDF generator
• The PDF generator generates the invoice and saves it on the file system
• The UI checks every couple seconds if the invoice is available

Requirements

There are four services that work on the message queue:
• DataCollectionDispatcher
o Starts the data gathering job
o Has knowledge about the available stations
o Sends a message for every charging station to the StationDataCollector o Sends a message to the DataCollectionReciever, that a new job started
• StationDataCollector
o Gathers data for a specific customer from a specific charging station o Sends data to the DataCollectionReciever
• DataCollectionReciever
o Receives all collected data
o Sortthedatatotheaccordinggatheringjob
o Sends data to the PdfGenerator when the data is complete • PdfGenerator
o Generatestheinvoicefromdata o Saves PDf to the file system
