package edu.uth.sbmi.swrldemo;
/** Localizable strings for {@link edu.uth.sbmi.swrldemo}. */
class Bundle {
    /**
     * @return <i>UC1</i>
     * @see UC1TopComponent
     */
    static String CTL_UC1Action() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC1Action");
    }
    /**
     * @return <i>Use Case 1 Window</i>
     * @see UC1TopComponent
     */
    static String CTL_UC1TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC1TopComponent");
    }
    /**
     * @return <i>This is a UC1 window</i>
     * @see UC1TopComponent
     */
    static String HINT_UC1TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HINT_UC1TopComponent");
    }
    private Bundle() {}
}
