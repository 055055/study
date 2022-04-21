package leetcode.problems.easy._21;

public class MergeTwoSortedLists {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode listNode = mergeTwoListsSolution(list1, list2);
        while (listNode.next != null) {
            System.out.println("listNode = " + listNode.val);
            listNode = listNode.next;
        }

    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode l1 = list1;
        ListNode l2 = list2;

        ListNode resultList = new ListNode(0, null);
        ListNode head = resultList;

        while ((l1 != null) && (l2 != null)) {
            if (l1.val <= l2.val) {
                resultList.next = l1;
                l1 = l1.next;
            } else {
                resultList.next = l2;
                l2 = l2.next;
            }
            resultList = resultList.next;
        }

        if (l1 != null) {
            resultList.next = l1;
        }

        if (l2 != null) {
            resultList.next = l2;
        }

        return head.next;
    }

    public static ListNode mergeTwoListsSolution(ListNode l1, ListNode l2){
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.val < l2.val){
            l1.next = mergeTwoListsSolution(l1.next, l2);
            return l1;
        } else{
            l2.next = mergeTwoListsSolution(l1, l2.next);
            return l2;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}

