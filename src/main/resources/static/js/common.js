$(function () {
  // message area
  $('.message .close')
      .on('click', function () {
        $(this).closest('.message')
            .transition('fade');
      });

  // dropdown
  $('.ui.dropdown').dropdown();

});
