(function() {
    $(document).ready(function(){
        $(".comment-list .btn-publish").click(function() {
            if(!$("#content").val()) {
                return;
            }
            var content = $("#content").val();
            $("#content").val("");
            $.post(location.pathname + "/comments", {
                content: content
            }, function(data) {
                var commentElement = $($(".comment-template").html());
                $(commentElement.find(".author-name")).html(data.commentor.name);
                $(commentElement.find(".author-name")).attr("href","/"+data.commentor.name);
                $(commentElement.find(".comment-time")).html(format(data.createdTime));
                $(commentElement.find(".comment-content")).html(data.content);
                $(".comment-list ul.list-unstyled").append(commentElement);
            });
        });
    });
})();

function add0(m){return m<10?'0'+m:m }
function format(date)
{
    var time = new Date(date);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return add0(m)+'月'+add0(d)+'日 '+add0(h)+':'+add0(mm);
}