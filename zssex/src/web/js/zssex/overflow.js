(function(){var d=0;var g=1;var c=2;var f=3;var a=4;var b=5;var e={};zk.override(zss.Cell.prototype,e,{_processOverflow:function(){var h=this.c;var j=this.rborder;var p=jq(this.$n());var r=this.$n("cave");var o=jq(r);var t=this.getTextNode();var n=jq(t);if(zk.ie6_||zk.ie7_){o.css("width","")}else{n.css("width","")}var m=this.sheet;var l=m.custColWidth;var i=l.getSize(h);var q=t.scrollWidth;var k=m.cellPad;if(q<=l.getSize(this.c)-k){p.removeClass("zscell-overflow");p.removeClass("zscell-overflow-b");return}var s=this.nextSibling;while(s&&s.cellType===f&&i<q){i+=l.getSize(s.c);s=s.nextSibling}i-=k;p.removeClass(j?"zscell-overflow":"zscell-overflow-b").addClass(j?"zscell-overflow-b":"zscell-overflow");if(zk.ie6_||zk.ie7_){o.css("width",jq.px0(i))}else{n.css("width",jq.px0(i))}}})})();