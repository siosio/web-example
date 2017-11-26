var contextPath = $("meta[name='contextPath']").attr("content");

$(function () {
  var $clientList = $('#client-list');

  $clientList.tabulator({
    layout: "fitColumns",
    columns: [
      {title: 'ID', field: 'clientId'},
      {title: '顧客名', field: 'name'}
    ],
    rowClick: function (e, row) {
      var data = row.row.data;
      $('#client-id').val(data.clientId);
      $('#client-name').val(data.name);
      $('#dialog').modal('hide');
    }
  });

  $('#dialog')
      .modal({
        onShow: function () {
          $clientList.tabulator('setData', page.CONTEXT_ROOT + '/clients/search');
        }
      });
});

