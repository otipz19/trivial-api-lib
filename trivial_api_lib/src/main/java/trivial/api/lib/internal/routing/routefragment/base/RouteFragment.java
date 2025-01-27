package trivial.api.lib.internal.routing.routefragment.base;

public abstract class RouteFragment {
    protected final String fragment;

    public RouteFragment(String fragment) {
        this.fragment = fragment.toLowerCase();
    }

    public abstract boolean matches(String fragment);
}
