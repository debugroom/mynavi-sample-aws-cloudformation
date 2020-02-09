function sendText() {
    var dynamodbTable = $("#dynamodb-table");
    if(dynamodbTable != null){
        dynamodbTable.remove();
    }
    $("#dynamodb-access-headline").after($('<table id="dynamodb-table">'
        +  '<thead>'
        +    '<tr>'
        +      '<th>No</th>'
        +      '<th>Partition Key</th>'
        +      '<th>Sort Key</th>'
        +      '<th>Text</th>'
        +    '</tr>'
        +  '</thead>'
        +  '<tbody>'
        +  '</tbody>'
        +'</table>'));
    var form = {
        "message" : $("#sampleText").val()
    }
    $.ajax({
        type : "post",
        url : "message",
        data : JSON.stringify(form),
        dataType : "json",
        contentType : 'application/json',
    }).then(
        function(data){
            $.each(data, function (i, val) {
                $("#dynamodb-table tbody")
                    .append($('<tr>'
                        +   '<td>'
                        + (i + 1)
                        +   '</td>'
                        +   '<td>'
                        + val.samplePartitionKey
                        +   '</td>'
                        +   '<td>'
                        + val.sampleSortKey
                        +   '</td>'
                        +   '<td>'
                        + val.sampleText
                        +   '</td>'
                        + '</tr>' ));
            });
        },
        function(data){

        }
    );
}