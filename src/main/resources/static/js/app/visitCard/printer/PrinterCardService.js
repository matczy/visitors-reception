(function () {
    "use strict";
    angular.module('visitorApp').factory('printerCardService', printerCardService);

    printerCardService.$inject = ['printerCardGenerator','messageService'];

    function printerCardService(printerCardGenerator,messageService) {

        var service = angular.merge(this, {
            print: print
        });

        return service;



        function print(registerVisitor) {
            var labelXml = _getVisitCardTemplate(registerVisitor.person,registerVisitor.contactPerson, registerVisitor.id);
            try
            {
                var label = dymo.label.framework.openLabelXml(labelXml);

                var printers = dymo.label.framework.getPrinters();
                if (printers.length == 0)
                    throw "No DYMO printers are installed. Install DYMO printers.";

                var printerName = "";
                for (var i = 0; i < printers.length; ++i)
                {
                    var printer = printers[i];
                    if (printer.printerType == "LabelWriterPrinter")
                    {
                        printerName = printer.name;
                        break;
                    }
                }

                if (printerName == "")
                    throw "No LabelWriter printers found. Install LabelWriter printer";

                // finally print the label
                label.print(printerName);
                messageService.showInfoMessage('Rozpoczęcie drukowania przepustki');

            }
            catch(e)
            {

                //TODO poprawic to na message
                alert(e.message || e);
            }
        }


        function _getVisitCardTemplate(person, contactPerson, id) {
            var labelXml;
            if (contactPerson) {
                labelXml = printerCardGenerator.getVisitCardWithContactPersonData(person, contactPerson, id);
            } else {
                labelXml = printerCardGenerator.getVisitCardWithoutContactPerson(person, id);
            }

            return labelXml;
        }

    }
})();


