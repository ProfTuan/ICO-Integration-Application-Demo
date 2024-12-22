package edu.uth.sbmi;
/** Localizable strings for {@link edu.uth.sbmi}. */
class Bundle {
    /**
     * @return <i>UC2</i>
     * @see UC2TopComponent
     */
    static String CTL_UC2Action() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC2Action");
    }
    /**
     * @return <i>Use Case 2 Window</i>
     * @see UC2TopComponent
     */
    static String CTL_UC2TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_UC2TopComponent");
    }
    /**
     * @return <i>This is a UC2 window</i>
     * @see UC2TopComponent
     */
    static String HINT_UC2TopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HINT_UC2TopComponent");
    }
    private Bundle() {}
}
