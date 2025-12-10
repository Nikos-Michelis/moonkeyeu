const BuildProviderTree = (componentsWithProps) => {
    const initialComponent = ({children}) => <>{children}</>;
    return componentsWithProps?.reduce(
        (AccumulatedComponents, [Provider, props = {}]) => {
            return ({children}) => (
                <AccumulatedComponents>
                    <Provider {...props}>{children}</Provider>
                </AccumulatedComponents>
            );
        },
        initialComponent
    );
}

export default BuildProviderTree;