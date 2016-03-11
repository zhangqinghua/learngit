/**
 * 对一个对象进行排序
 */
var list = {"you": 100, "me": 75, "foo": 116, "bar": 15};
keysSorted = Object.keys(list).sort(function(a,b){return list[a]-list[b]})
alert(keysSorted); 