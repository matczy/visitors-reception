(function () {
    "use strict";
    angular.module('visitorApp').factory('printerCardGenerator', printerCardGenerator);

    printerCardGenerator.$inject = ['$log'];

    function printerCardGenerator($log) {

        var service = angular.merge(this, {
            getVisitCardWithContactPersonData: getVisitCardWithContactPersonData,
            getVisitCardWithoutContactPerson: getVisitCardWithoutContactPerson
        });

        return service;





        function getVisitCardWithContactPersonData(person,contactPerson, id) {
            return '<?xml version="1.0" encoding="utf-8"?>\
                        <DieCutLabel Version="8.0" Units="twips">\
                    <PaperOrientation>Landscape</PaperOrientation>\
                    <Id>WhiteNameBadge11356</Id>\
                    <PaperName>11356 White Name Badge - virtual</PaperName>\
                <DrawCommands>\
                <RoundRectangle X="0" Y="0" Width="2340" Height="5040" Rx="270" Ry="270" />\
                    </DrawCommands>\
                    <ObjectInfo>\
                    <TextObject>\
                    <Name>TEKST</Name>\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>False</IsVariable>\
                    <HorizontalAlignment>Left</HorizontalAlignment>\
                    <VerticalAlignment>Top</VerticalAlignment>\
                    <TextFitMode>ShrinkToFit</TextFitMode>\
                    <UseFullFontHeight>True</UseFullFontHeight>\
                    <Verticalized>False</Verticalized>\
                    <StyledText>\
                    <Element>\
                    <String>'+person.surname + ' '+person.name+', '+person.company.name+'\</String>\
                <Attributes>\
                <Font Family="Arial" Size="16" Bold="False" Italic="True" Underline="False" Strikeout="False" />\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    </Attributes>\
                    </Element>\
                    </StyledText>\
                    </TextObject>\
                    <Bounds X="331.200012207031" Y="150" Width="4622.39990234375" Height="615" />\
                    </ObjectInfo>\
                    <ObjectInfo>\
                    <TextObject>\
                    <Name>TEKST_1</Name>\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>False</IsVariable>\
                    <HorizontalAlignment>Left</HorizontalAlignment>\
                    <VerticalAlignment>Top</VerticalAlignment>\
                    <TextFitMode>ShrinkToFit</TextFitMode>\
                    <UseFullFontHeight>True</UseFullFontHeight>\
                    <Verticalized>False</Verticalized>\
                    <StyledText>\
                    <Element>\
                    <String>Os. kont: </String>\
                <Attributes>\
                <Font Family="Arial" Size="12" Bold="False" Italic="False" Underline="False" Strikeout="False" />\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    </Attributes>\
                    </Element>\
                    <Element>\
                    <String>'+contactPerson.surname + ' '+contactPerson.name+'\
                </String>\
                <Attributes>\
                <Font Family="Arial" Size="12" Bold="False" Italic="True" Underline="False" Strikeout="False" />\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    </Attributes>\
                    </Element>\
                    <Element>\
                    <String>tel: </String>\
                <Attributes>\
                <Font Family="Arial" Size="12" Bold="False" Italic="False" Underline="False" Strikeout="False" />\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    </Attributes>\
                    </Element>\
                    <Element>\
                    <String>'+contactPerson.phone+'\</String>\
                <Attributes>\
                <Font Family="Arial" Size="12" Bold="False" Italic="True" Underline="False" Strikeout="False" />\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    </Attributes>\
                    </Element>\
                    </StyledText>\
                    </TextObject>\
                    <Bounds X="331.200012207031" Y="1570.40000152588" Width="4622.39990234375" Height="690" />\
                    </ObjectInfo>\
                    <ObjectInfo>\
                    <BarcodeObject>\
                    <Name>KOD KRESKOWY</Name>\
                <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>True</IsVariable>\
                    <Text>'+id+'\</Text>\
                    <Type>Code128Auto</Type>\
                    <Size>Medium</Size>\
                    <TextPosition>Bottom</TextPosition>\
                    <TextFont Family="Arial" Size="8" Bold="False" Italic="False" Underline="False" Strikeout="False" />\
                    <CheckSumFont Family="Arial" Size="8" Bold="False" Italic="False" Underline="False" Strikeout="False" />\
                    <TextEmbedding>None</TextEmbedding>\
                    <ECLevel>0</ECLevel>\
                    <HorizontalAlignment>Center</HorizontalAlignment>\
                    <QuietZonesPadding Left="0" Top="0" Right="0" Bottom="0" />\
                    </BarcodeObject>\
                    <Bounds X="2101.20001220703" Y="810" Width="2852.39990234375" Height="720" />\
                    </ObjectInfo>\
                    <ObjectInfo>\
                    <ShapeObject>\
                    <Name>KSZTAŁT</Name>\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>False</IsVariable>\
                    <ShapeType>HorizontalLine</ShapeType>\
                    <LineWidth>15</LineWidth>\
                    <LineAlignment>Center</LineAlignment>\
                    <FillColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    </ShapeObject>\
                    <Bounds X="331.200012207031" Y="690" Width="4573.79998779297" Height="15" />\
                    </ObjectInfo>\
                    </DieCutLabel>';
        }

        function getVisitCardWithoutContactPerson(person,id) {
            return '<?xml version="1.0" encoding="utf-8"?>\
                        <DieCutLabel Version="8.0" Units="twips">\
                    <PaperOrientation>Landscape</PaperOrientation>\
                    <Id>WhiteNameBadge11356</Id>\
                    <PaperName>11356 White Name Badge - virtual</PaperName>\
                <DrawCommands>\
                <RoundRectangle X="0" Y="0" Width="2340" Height="5040" Rx="270" Ry="270" />\
                    </DrawCommands>\
                    <ObjectInfo>\
                    <TextObject>\
                    <Name>TEKST</Name>\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>False</IsVariable>\
                    <HorizontalAlignment>Left</HorizontalAlignment>\
                    <VerticalAlignment>Top</VerticalAlignment>\
                    <TextFitMode>ShrinkToFit</TextFitMode>\
                    <UseFullFontHeight>True</UseFullFontHeight>\
                    <Verticalized>False</Verticalized>\
                    <StyledText>\
                    <Element>\
                    <String>'+person.surname + ' '+person.name+', '+person.company.name+'\</String>\
                <Attributes>\
                <Font Family="Arial" Size="12" Bold="False" Italic="True" Underline="False" Strikeout="False" />\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    </Attributes>\
                    </Element>\
                    </StyledText>\
                    </TextObject>\
                    <Bounds X="331.200012207031" Y="1570.40000152588" Width="4622.39990234375" Height="690" />\
                    </ObjectInfo>\
                    <ObjectInfo>\
                    <BarcodeObject>\
                    <Name>KOD KRESKOWY</Name>\
                <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>True</IsVariable>\
                    <Text>'+id+'\</Text>\
                    <Type>Code128Auto</Type>\
                    <Size>Medium</Size>\
                    <TextPosition>Bottom</TextPosition>\
                    <TextFont Family="Arial" Size="8" Bold="False" Italic="False" Underline="False" Strikeout="False" />\
                    <CheckSumFont Family="Arial" Size="8" Bold="False" Italic="False" Underline="False" Strikeout="False" />\
                    <TextEmbedding>None</TextEmbedding>\
                    <ECLevel>0</ECLevel>\
                    <HorizontalAlignment>Center</HorizontalAlignment>\
                    <QuietZonesPadding Left="0" Top="0" Right="0" Bottom="0" />\
                    </BarcodeObject>\
                    <Bounds X="2101.20001220703" Y="810" Width="2852.39990234375" Height="720" />\
                    </ObjectInfo>\
                    <ObjectInfo>\
                    <ShapeObject>\
                    <Name>KSZTAŁT</Name>\
                    <ForeColor Alpha="255" Red="0" Green="0" Blue="0" />\
                    <BackColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    <LinkedObjectName></LinkedObjectName>\
                    <Rotation>Rotation0</Rotation>\
                    <IsMirrored>False</IsMirrored>\
                    <IsVariable>False</IsVariable>\
                    <ShapeType>HorizontalLine</ShapeType>\
                    <LineWidth>15</LineWidth>\
                    <LineAlignment>Center</LineAlignment>\
                    <FillColor Alpha="0" Red="255" Green="255" Blue="255" />\
                    </ShapeObject>\
                    <Bounds X="331.200012207031" Y="690" Width="4573.79998779297" Height="15" />\
                    </ObjectInfo>\
                    </DieCutLabel>';
        }

    }
})();


