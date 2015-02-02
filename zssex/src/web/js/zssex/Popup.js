(function(){function e(l,r,g){var s=l.topHeight;var j=l.leftWidth;var m=l.custColWidth;var t=l.custRowHeight;var i=m.getStartPixel(g);var o=t.getStartPixel(r);var q=m.getStartPixel(g+1)-1;var f=t.getStartPixel(r+1)-1;if(r>l.frozenRow){o-=l.spcmp.scrollTop;f-=l.spcmp.scrollTop}if(g>l.frozenCol){i-=l.spcmp.scrollLeft;q-=l.spcmp.scrollLeft}var p=q-i+1;var k=f-o+1;return{top:o+s,left:i+j,bottom:f+s,right:q+j,width:p,height:k}}zssex.Popup=zk.$extends(zk.Widget,{$o:zk.$void,$init:function(f){this.$supers(zssex.Popup,"$init",[]);this.sheet=f},open:function(){},close:function(){this.setVisible(false)}});zssex.CellPopup=zk.$extends(zssex.Popup,{widgetName:"CellPopup",isOpen:function(){return this.$n()&&this.isVisible()},open:function(f,h){if(!this.isOpen()){var g=this.$n();if(!g){this.createPopup_()}this.relocate(f,h);this.setVisible(true)}},relocate:function(h,f){var g=e(this.sheet,h,f);jq(this.$n()).css({left:jq.px(g.left),top:jq.px(g.bottom),width:jq.px(g.width)})},createPopup_:function(){jq(this.sheet.$n()).append(this.redrawHTML_());this.clearCache();this.bind_()},redrawHTML_:function(){return this.prologHTML_()+this.contentHTML_()+this.epilogHTML_()},contentHTML_:function(){return""},prologHTML_:function(){return"<div "+this.domAttrs_()+">"},epilogHTML_:function(){return"</div>"},listenSheetFocused_:function(f){var g=!!this._listenSheetFocused;if(g!=f){this.sheet[f?"listen":"unlisten"]({onFocused:this.proxy(this.onSheetFocused)});this._listenSheetFocused=f}},onSheetFocused:function(f){},listenSheetBlur_:function(f){var g=!!this._listenSheetBlur;if(g!=f){this.sheet._wgt[f?"listen":"unlisten"]({onBlur:this.proxy(this.onSheetBlur)});this._listenSheetBlur=f}},onSheetBlur:function(f){},getZclass:function(){return"zscellpopup"}});zssex.ValidationPromptPopup=zk.$extends(zssex.CellPopup,{widgetName:"ValidationPromptPopup",$init:function(f,h,g){this.$supers(zssex.ValidationPopup,"$init",arguments);this._pos={};this._title=h;this._text=g},unbind_:function(){delete this._pos;this._title=this._text=null;this.listenSheetFocused_(false);this.$supers(zssex.ValidationPromptPopup,"unbind_",arguments)},open:function(g,h){if(!this.isOpen()){this.$supers(zssex.ValidationPromptPopup,"open",arguments);if(zk.ie6_||zk.ie7_||zk.opera){jq(this.$n()).addClass("zsdvp-popup")}var f=this;setTimeout(function(){f.listenSheetFocused_(true)})}},close:function(){var f=this;setTimeout(function(){f.listenSheetFocused_(false)});this.$supers(zssex.ValidationPromptPopup,"close",arguments)},relocate:function(p,g){var o=245,h=3,i=6,l=jq(this.$n());var k=this.sheet,f=e(this.sheet,p,g),m=this._pos;m.row=p;m.col=g;l.css({left:jq.px(f.right+h),top:jq.px(f.bottom+i)});if(!zk.ie6_&&!zk.ie7_){var j=zk.parseInt(l.width());if(j>o){l.width(jq.px(o))}}},onSheetFocused:function(f){var j=f.data,h=this._pos,g=j.col,i=j.row;if(h.row!=i||h.col!=g){this.close()}},contentHTML_:function(){var f=this.uuid,h=this._title,g=this._text;return'<div id="'+f+'-cave" class="zsdvp-popup-cave"><div id="'+f+'-title" class="zsdvp-title">'+(h?h:"")+'</div><div id="'+f+'-text" class="zsdvp-text">'+(g?g:"")+"</div></div>"}});function c(g){if(g.tagName.toLowerCase()=="a"){return g}var f=g.firstChild;return f&&f.tagName.toLowerCase()=="a"?f:null}function d(i,f,g){var h=g.state;g.state=zss.SSheetCtrl.FOCUSED;g.moveCellFocus(i,f,true);g.moveCellSelection(f,i,f,i,false,true);g.state=h}zssex.ValidationPopup=zk.$extends(zssex.CellPopup,{widgetName:"ValidationPopup",$init:function(f){this.$supers(zssex.ValidationPopup,"$init",arguments);this._pos={};this._validationList=[]},bind_:function(){this.$supers(zssex.ValidationPopup,"bind_",arguments);var f=this._validationList.length,g=this.$n();if(g){if(f>=8){jq(g).css({overflow:"auto",height:"155px"})}else{jq(g).css({overflow:"hidden",height:""})}}},unbind_:function(){delete this._pos;delete this._validationList;this.listenSheetBlur_(false);this.listenSheetFocused_(false);this.$supers(zssex.ValidationPopup,"unbind_",arguments)},_markSelectedItem:function(k,f){var h=this.sheet,j=h.getCell(k,f).getPureText(),i=this.$n("cave").firstChild;if(i){for(;i;i=i.nextSibling){var l=jq(i.firstChild),g=l.text();jq(i)[j==g?"addClass":"removeClass"]("zsdv-item-over")}}},open:function(f,g){if(this.isOpen()){this.close()}else{this.$supers(zssex.ValidationPopup,"open",arguments);this._markSelectedItem(f,g);this.listenSheetBlur_(true);this.listenSheetFocused_(true)}},close:function(){var f=this;setTimeout(function(){f.listenSheetBlur_(false);f.listenSheetFocused_(false);f.sheet.dp.gainFocus(false)});this.$supers(zssex.ValidationPopup,"close",arguments)},onSheetFocused:function(f){this.close()},relocate:function(l,f){var g=this.sheet,k=e(this.sheet,l,f),i=17,h=k.width+i,j=k.right+i-h,m=this._pos;m.row=l;m.col=f;jq(this.$n()).css({left:jq.px(j),top:jq.px(k.bottom),width:jq.px(h)})},setValidationList:function(j){var f=this._validationList,h=j.length,g=h;f.splice(0,f.length);while(g--){f[g]=j[g]}var k=this.$n();if(k){if(h>=8){jq(k).css({overflow:"auto",height:"155px"})}else{jq(k).css({overflow:"hidden",height:""})}}},contentHTML_:function(){var j=this.uuid,h='<ul id="'+j+'-cave" class="zsdv-popup-cave">',k=this._validationList,g=k.length;for(var f=0;f<g;f++){h+='<li class="zsdv-item"><a href="javascript:" class="zsdv-item-text">'+k[f]+"</a></li>"}h+='</ul><a id="'+j+'-fo" href="javascript:"></a>';return h},_editCell:function(k,f,j){var g=this.sheet,i=g.getCell(k,f).getPureText();if(i!=j){var h=g.state;if(g.state!=zss.SSheetCtrl.START_EDIT){g.state=zss.SSheetCtrl.START_EDIT;g._wgt.fire("onStartEditing",{token:"",sheetId:g.serverSheetId,row:k,col:f,clienttxt:j,type:"inlineEditing"},null,25)}g.state=h;g._wgt.fire("onStopEditing",{token:"",sheetId:g.serverSheetId,row:k,col:f,value:j,type:"inlineEditing"},{toServer:true},25)}},onSheetBlur:function(){if(this.isOpen()){var f=jq(this.$n()).find(".zsdv-item-over"),g=this._pos;row=g.row,col=g.col;if(f.length){jq(f[0].firstChild).focus()}else{jq(this.$n("fo")).focus()}d(g.row,g.col,this.sheet)}},doMouseOver_:_zkf=function(f){var g=c(f.domTarget);if(g){var h=jq(this.$n()).find(".zsdv-item-over");if(h.length){h.removeClass("zsdv-item-over")}jq(g.parentNode).addClass("zsdv-item-over")}},doMouseMove_:_zkf,doKeyPress_:function(f){},doMouseDown_:function(f){var h=c(f.domTarget);if(h){var g=this._pos;this._editCell(g.row,g.col,jq(h).text());this.close()}},doKeyDown_:function(i){var h=i.keyCode,k=document.activeElement==this.$n("fo"),m=null,j=null;switch(h){case 38:case 40:if(k){j=this.$n("cave").firstChild}else{m=jq(this.$n()).find(".zsdv-item-over");if(m.length){j=h==38?m[0].previousSibling:m[0].nextSibling}}if(j){jq(j).addClass("zsdv-item-over");jq(j.firstChild).focus()}if(j&&m){m.removeClass("zsdv-item-over")}break;case 13:var g=jq(this.$n()).find(".zsdv-item-over");if(g.length){var l=this._pos;this._editCell(l.row,l.col,jq(g[0]).text())}this.close();break;case 27:this.close();break}i.stop()}});function b(g){var f=jq(g);return f.hasClass("zsafp-item")?f[0]:f.hasClass("zsafp-item-checkbox")||(zk.ie6_&&f.hasClass("zsafp-item-text"))?f[0].parentNode:null}function a(h){if(h==null){return}var g=h.parentNode,f=g.firstChild;for(;f;f=f.nextSibling){if(f!=h){jq(f).removeClass("zsafp-item-over")}}jq(h).addClass("zsafp-item-over")}zssex.AutoFilterPopup=zk.$extends(zssex.CellPopup,{$init:function(g,f){this.$supers(zssex.AutoFilterPopup,"$init",arguments);this._pos={};this._field=f.field;this._rangeAddr=f.range;this._blank=f.blank;this._select=f.select;this._initItems(f.items,f.select,f.blank);this.setVisible(false)},_initItems:function(g,f,l){var k=this._items=[{v:zssex.AutoFilterPopup.SELECT_ALL,s:f}],j=g.length;for(var h=0;h<j;h++){k.push(g[h])}if(l!=undefined){k.push({v:zssex.AutoFilterPopup.BLANK,s:l})}},unbind_:function(){delete this._pos;delete this._items;delete this._$is;this.listenSheetFocused_(false);var f=this.$n("inp");this.domUnlisten_(f,"onKeyDown",this._onFilterKeyDown);this.domUnlisten_(f,"onKeyUp",this._onFilterKeyUp);this.$supers(zssex.AutoFilterPopup,"unbind_",arguments)},open:function(h,j){if(this.isOpen()){this.close()}else{this.$supers(zssex.AutoFilterPopup,"open",arguments);var g=this.$n("inp"),f=this._select,i=jq(this._$items()[0]);g.focus();this.sheet.state=zss.SSheetCtrl.NOFOCUS;this.domListen_(g,"onKeyDown",this._onFilterKeyDown);this.domListen_(g,"onKeyUp",this._onFilterKeyUp);if("all"==f){i.addClass("zsafp-item-seld")}else{if("mix"==f){i.addClass("zsafp-item-seld-mix")}}this.listenSheetFocused_(true)}},_$items:function(){if(!this._$is){this._$is=jq(this.$n("items")).children()}return this._$is},_onFilterKeyDown:function(f){this._prevFilterVal=f.domTarget.value},_onFilterKeyUp:function(g){var h=g.domTarget.value.toLowerCase(),f=this._prevFilterVal.length>h.length,i=this._$items();i.each(function(j,l){var k=jq(l);if(f){if(k.hasClass("zsafp-item-hide")){if(k.text().toLowerCase().indexOf(h)>=0){k.removeClass("zsafp-item-hide")}}}else{if(k.text().toLowerCase().indexOf(h)<0){k.addClass("zsafp-item-hide")}}})},close:function(){var f=this;setTimeout(function(){f.listenSheetFocused_(false);f.sheet.dp.gainFocus(false)});this.$supers(zssex.AutoFilterPopup,"close",arguments);this.detach()},contentHTML_:function(){var k=this.uuid,f=this._items,j=f.length,h='<div class="zsafp-popup-cave">';h+='<div class="zsafp-search"><div class="zsafp-search-cnt"><input id="'+k+'-inp" class="zsafp-search-inp" type="text" autocomplete="off" tabindex="0"></input><div class="zsafp-search-icon"></div></div></div>';h+='<div id="'+k+'-items" class="zsafp-items">';for(var g=0;g<j;g++){var l=f[g];h+='<div class="zsafp-item'+(l.s?" zsafp-item-seld":"")+'"><div class="zsafp-item-checkbox"></div>';if(zk.ie6_){h+='<div class="zsafp-item-text">'+l.v+"</div></div>"}else{h+=(l.v+"</div>")}}h+='</div><div id="'+k+'-btns" class="zsafp-btns"><div id="'+k+'-okBtn" class="zsafp-btn zsafp-ok-btn"></div><div id="'+k+'-cancelBtn" class="zsafp-btn zsafp-cancel-btn"></div></div></div>';return h},onSheetFocused:function(f){this.close()},relocate:function(l,f){var h=jq(this.$n());if((zk.ie6_||zk.ie7_)&&!h.width()){h.addClass("zsafp-popup")}var g=this.sheet,i=g.leftWidth,k=e(this.sheet,l,f),j=Math.max(i,k.right-zk.parseInt(h.width()));jq(this.$n()).css({left:jq.px(j),top:jq.px(k.bottom)})},doMouseOver_:function(f){a(b(f.domTarget))},doMouseMove_:function(f){},_selectNone:function(){var h=this._items,g=this._$items(),j=h.length;while(j--){var m=h[j],l=m.s,k=jq(g[j]);if(j==0){m.s="none";var f=jq(this.$n("okBtn"));if("mix"==l){k.removeClass("zsafp-item-seld-mix");f.addClass("zsafp-btn-disd")}else{if("all"==l){k.removeClass("zsafp-item-seld");f.addClass("zsafp-btn-disd")}}}else{m.s=false;if(l){k.removeClass("zsafp-item-seld")}}}},_selectAll:function(){var g=this._items,f=this._$items(),h=g.length;while(h--){var l=g[h],k=l.s,j=jq(f[h]);if(h==0){l.s="all";if("mix"==k){j.removeClass("zsafp-item-seld-mix").addClass("zsafp-item-seld")}else{if("none"==k){j.addClass("zsafp-item-seld");jq(this.$n("okBtn")).removeClass("zsafp-btn-disd")}}}else{l.s=true;if(!k){j.addClass("zsafp-item-seld")}}}},_isSelectAll:function(){return"all"==this._items[0].s},_isSelectNone:function(){return"none"==this._items[0].s},_updateSelectAllItem:function(){var g=this._items,h=g.length,k=true,f=false,m=this._items[0].s;while(h--){var l=g[h];if(h==0){var j=jq(this._$items()[0]);if(k){j.addClass("zsafp-item-seld");l.s="all"}else{if(f){j.addClass("zsafp-item-seld-mix");l.s="mix"}else{l.s="none";jq(this.$n("okBtn")).addClass("zsafp-btn-disd")}}if(m!=l.s){if("all"==m){j.removeClass("zsafp-item-seld")}else{if("mix"==m){j.removeClass("zsafp-item-seld-mix")}else{jq(this.$n("okBtn")).removeClass("zsafp-btn-disd")}}}}else{if(l.s){f=true}else{k=false}}}},_clickItem:function(m){var f=this._items,l=f.length,g=0,k=false;n=this._$items()[0];for(;n;n=n.nextSibling,g++){if(n==m){var j=f[g];if(g==0){var h=j.s;if("all"==h){this._selectNone()}else{if("mix"==h||"none"==h){this._selectAll()}}}else{k=true;jq(m)[j.s?"removeClass":"addClass"]("zsafp-item-seld");j.s=!j.s}break}}if(k){this._updateSelectAllItem()}},_isButton:function(h){var f=this.$n("okBtn"),g=this.$n("cancelBtn");if(zUtl.isAncestor(f,h)){return"ok"}else{if(zUtl.isAncestor(g,h)){return"cancel"}}},doMouseDown_:function(g){var j=g.domTarget,h=this._isButton(j);if(h){if("ok"==h){if(!this._isSelectNone()){this._applyFilter();this.close()}}else{if("cancel"==h){this.close()}}return}var k=b(j),i=this.$n("inp");if(k){this._clickItem(k)}setTimeout(function(){i.focus()},0)},doKeyPress_:function(f){},_getCriteria:function(){var g=[],f=this._items,j=f.length,l=j-1,m=this._blank;for(var h=1;h<j;h++){var k=f[h];if(h==l&&m!=undefined){if(k.s){g.push("=")}}else{if(k.s){g.push(k.v)}}}return g},_applyFilter:function(){var f={type:"apply",field:this._field,range:this._rangeAddr};f.all=this._isSelectAll();if(!f.all){f.criteria=this._getCriteria()}this.sheet._wgt.fire("onZSSFilter",f,{toServer:true})},_prevVisibleItem:function(g){for(var f=g.previousSibling;f;f=f.previousSibling){if(f&&f.className.indexOf("zsafp-item-hide")<0){return f}}},_nextVisibleItem:function(g){for(var f=g.nextSibling;f;f=f.nextSibling){if(f&&f.className.indexOf("zsafp-item-hide")<0){return f}}},_firstVisibleItem:function(){for(var f=this._$items()[0];f;f=f.nextSibling){if(f&&f.className.indexOf("zsafp-item-hide")<0){return f}}},doKeyDown_:function(g){var f=g.keyCode;switch(f){case 38:case 40:var h=this.$n("items"),i=jq(h).children(".zsafp-item-over")[0];if(i){var j=(f==38)?this._prevVisibleItem(i):this._nextVisibleItem(i);if(j){jq(j).addClass("zsafp-item-over");jq(i).removeClass("zsafp-item-over")}}else{jq(this._firstVisibleItem()).addClass("zsafp-item-over")}break;case 13:if(!this._isSelectNone()){this._applyFilter();this.close()}break;case 27:this.close()}}},{SELECT_ALL:"(Select All)",BLANK:"(Blanks)"})})();