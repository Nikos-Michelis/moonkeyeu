import { useEffect } from "react";

/**
 * useClickOutside hook
 *
 * Detects clicks outside a referenced modal/popover element and optionally ignores the trigger ref.
 * Calls the provided handler when a user clicks outside the modalRef, excluding the triggerRef.
 *
 * Common use cases include closing dropdowns, modals, tooltips, or popovers when the user clicks outside them.
 *
 * @param {React.RefObject<HTMLElement>} modalRef - Ref to the modal or dropdown container element.
 * @param {React.RefObject<HTMLElement>} triggerRef - Ref to the element that triggered the modal (optional).
 * @param {Function} handler - Function to call when a click outside is detected.
 *
 * @example
 * const modalRef = useRef();
 * const triggerRef = useRef();
 *
 * useClickOutside(modalRef, triggerRef, () => {
 *   console.log("Clicked outside");
 * });
 */
export function useClickOutside({ modalRef, triggerRef = undefined, handler }) {
    useEffect(() => {
        const listener = (e) => {
            const clickedOutsideDropdown =
                modalRef.current && !modalRef.current.contains(e.target);
            const clickedTrigger =
                triggerRef?.current && triggerRef.current.contains(e.target);
            if (clickedOutsideDropdown && !clickedTrigger && handler) {
                handler(e);
            }
        };

        document.addEventListener("mousedown", listener);
        return () => {
            document.removeEventListener("mousedown", listener);
        };
    }, [modalRef, triggerRef, handler]);
}
