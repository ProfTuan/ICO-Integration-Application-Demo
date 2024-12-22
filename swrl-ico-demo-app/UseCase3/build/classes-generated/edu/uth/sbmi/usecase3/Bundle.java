package edu.uth.sbmi.usecase3;
/** Localizable strings for {@link edu.uth.sbmi.usecase3}. */
class Bundle {
    /**
     * @return <i>UC3</i>
     * @see UC3TopComponent
     */
    static String CTL_UC3Action() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC3Action");
    }
    /**
     * @return <i>Use Case 3 Window</i>
     * @see UC3TopComponent
     */
    static String CTL_UC3TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC3TopComponent");
    }
    /**
     * @return <i>This is a Use Case 3 window</i>
     * @see UC3TopComponent
     */
    static String HINT_UC3TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HINT_UC3TopComponent");
    }
    private Bundle() {}
}
