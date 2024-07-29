package gnu.lists;

public class PositionManager {
    static final PositionManager manager = new PositionManager();
    int freeListHead;
    int[] ivals;
    SeqPosition[] positions = new SeqPosition[50];

    public static SeqPosition getPositionObject(int ipos) {
        SeqPosition seqPosition;
        PositionManager m = manager;
        synchronized (m) {
            seqPosition = m.positions[ipos];
        }
        return seqPosition;
    }

    private void addToFreeList(int[] ivals2, int first, int end) {
        int head = this.freeListHead;
        for (int i = first; i < end; i++) {
            ivals2[i] = head;
            head = i;
        }
        this.freeListHead = head;
    }

    private int getFreeSlot() {
        int head = this.freeListHead;
        if (head < 0) {
            SeqPosition[] seqPositionArr = this.positions;
            int old_size = seqPositionArr.length;
            SeqPosition[] npositions = new SeqPosition[(old_size * 2)];
            int[] nvals = new int[(old_size * 2)];
            System.arraycopy(seqPositionArr, 0, npositions, 0, old_size);
            System.arraycopy(this.ivals, 0, nvals, 0, old_size);
            this.positions = npositions;
            this.ivals = nvals;
            addToFreeList(nvals, old_size, old_size * 2);
            head = this.freeListHead;
        }
        this.freeListHead = this.ivals[head];
        return head;
    }

    public PositionManager() {
        int[] iArr = new int[50];
        this.ivals = iArr;
        this.freeListHead = -1;
        addToFreeList(iArr, 1, iArr.length);
    }

    public synchronized int register(SeqPosition pos) {
        int i;
        i = getFreeSlot();
        this.positions[i] = pos;
        this.ivals[i] = -1;
        return i;
    }

    public synchronized void release(int ipos) {
        SeqPosition pos = this.positions[ipos];
        if (pos instanceof ExtPosition) {
            ((ExtPosition) pos).position = -1;
        }
        this.positions[ipos] = null;
        this.ivals[ipos] = this.freeListHead;
        this.freeListHead = ipos;
        pos.release();
    }
}
