package trivial.api.lib.internal.routing.routefragment;

import trivial.api.lib.internal.routing.routefragment.base.RouteFragment;

public class ExactRouteFragment extends RouteFragment {
    public ExactRouteFragment(String fragment) {
        super(fragment);
    }

    @Override
    public boolean matches(String fragment) {
        return this.fragment.compareToIgnoreCase(fragment) == 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExactRouteFragment otherExact) {
            return this.fragment.equals(otherExact.fragment);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.fragment.hashCode();
    }
}
