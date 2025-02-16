package gnu.xquery.util;

import gnu.kawa.xml.SortedNodes;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;

public class RelativeStepFilter extends FilterConsumer implements PositionConsumer {
    char seen;
    SortedNodes snodes;

    public RelativeStepFilter(Consumer base) {
        super(base);
    }

    public void consume(SeqPosition position) {
        writePosition(position.sequence, position.ipos);
    }

    public void writeObject(Object v) {
        if (v instanceof SeqPosition) {
            SeqPosition n = (SeqPosition) v;
            writePosition(n.sequence, n.ipos);
            return;
        }
        super.writeObject(v);
    }

    /* access modifiers changed from: protected */
    public void beforeContent() {
        if (this.seen != 'N') {
            this.seen = 'A';
            return;
        }
        throw new Error("path returns mix of atoms and nodes");
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        if (this.seen != 'A') {
            this.seen = 'N';
            if (this.snodes == null) {
                this.snodes = new SortedNodes();
            }
            this.snodes.writePosition(seq, ipos);
            return;
        }
        throw new Error("path returns mix of atoms and nodes");
    }

    public void finish() {
        SortedNodes sortedNodes = this.snodes;
        if (sortedNodes != null) {
            sortedNodes.consume(this.base);
        }
        this.snodes = null;
    }
}
