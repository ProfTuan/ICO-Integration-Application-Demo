package edu.uth.sbmi.usecase4;
/** Localizable strings for {@link edu.uth.sbmi.usecase4}. */
class Bundle {
    /**
     * @return <i>UC4</i>
     * @see UC4TopComponent
     */
    static String CTL_UC4Action() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC4Action");
    }
    /**
     * @return <i>Use Case 4 Window</i>
     * @see UC4TopComponent
     */
    static String CTL_UC4TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC4TopComponent");
    }
    /**
     * @return <i>This is a Use Case 4 window</i>
     * @see UC4TopComponent
     */
    static String HINT_UC4TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HINT_UC4TopComponent");
    }
    private Bundle() {}
}
