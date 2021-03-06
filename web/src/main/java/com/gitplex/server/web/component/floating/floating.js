gitplex.server.floating = {
	init: function(floatingId, alignment, closeCallback) {
		var $floating = $("#" + floatingId);
		$floating.data("closeCallback", closeCallback);
		
		$floating.data("mouseUpOrTouchStart", function(e) {
			/*
			 * Close the floating panel if mouse clicks outside of the floating. Also we 
			 * do not close the panel if mouse clicks on the element triggering this 
			 * floating panel, as normally the triggering element already has the logic 
			 * closing the floating when it is clicked (to achieve the toggle effect) 
			 */
			var x = e.pageX + $(window).scrollLeft();
			var y = e.pageY + $(window).scrollTop();
			
			// add this extra check as otherwise clicking on scroll bar of floating will hide the 
			// floating in IE
			var contains = $floating.offset().left<x && $floating.offset().left+$floating.outerWidth()>x
					&& $floating.offset().top<y && $floating.offset().top+$floating.outerHeight()>y;
					
		    if (!$floating.is(e.target) && $floating.has(e.target).length === 0 && !contains) {
		    	var $trigger = $floating.data("trigger");
			    if (!$trigger || !$trigger.is(e.target) && $trigger.has(e.target).length === 0) 
			    	gitplex.server.floating.close($floating, true);
		    }
		});
		$(document).on("mouseup touchstart", $floating.data("mouseUpOrTouchStart"));
		
		$floating.data("keydown", function(e) {
			if (e.keyCode == 27 && $(".select2-drop:visible").length == 0) {
				gitplex.server.floating.close($floating, true);
			}
		});
		
		// use keydown as keypress does not work in chrome/safari
		$(document).on("keydown", $floating.data("keydown"));
		
		if (alignment.target.element)
			$(alignment.target.element).addClass("floating-aligned");
		
		$floating.data("alignment", alignment);
		
		var openTriggered = false;
		$floating.data("elementReplaced", function() {
			$floating.align($floating.data("alignment"));
			if (!openTriggered) {
				$floating.trigger("open");
				openTriggered = true;
			}
		});
		$(document).on("elementReplaced", $floating.data("elementReplaced"));
	}, 
	
	close: function($floating, callCloseCallback) {
		if ($floating.length != 0) { // floating might already been closed
			if (callCloseCallback)
				$floating.data("closeCallback")();
			
			var alignment = $floating.data("alignment");
			
			if (alignment.target.element)
				$(alignment.target.element).removeClass("floating-aligned");
			
			$(document).off("mouseup touchstart", $floating.data("mouseUpOrTouchStart"));
			$(document).off("keydown", $floating.data("keydown"));
			$(document).off("elementReplaced", $floating.data("elementReplaced"));
			
			$floating.trigger("close");
			
			$floating.remove();
		}
	}
	
};