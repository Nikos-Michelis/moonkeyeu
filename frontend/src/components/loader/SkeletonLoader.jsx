import React from "react";
import PropTypes from "prop-types";
export function SkeletonLoader(
    {
        isPending,
        isFetching,
        isError,
        contentConfig = {
            count: 12,
            styles: {
                wrapper: "default-wrapper",
                img: "default-img"
            }
        },
        children
    }) {
    const Component = contentConfig?.component || null;
    if (isFetching || isPending || isError) {
        return (
            <>
                {Array.from({ length: contentConfig?.count ?? 12}).map((_, index) => (
                    Component && (
                        <Component
                            key={index}
                            skeletonStyle={contentConfig?.styles?.wrapper}
                            skeletonImgStyle={contentConfig?.styles?.img}
                        />
                    )
                ))}
            </>
        );
    }

    return <>{children}</>;
}

SkeletonLoader.propTypes = {
    isPending: PropTypes.bool,
    isFetching: PropTypes.bool,
    isError: PropTypes.bool,
    contentConfig: PropTypes.shape({
        component: PropTypes.elementType.isRequired,
        count: PropTypes.number,
        styles: PropTypes.shape({
            wrapper: PropTypes.string,
            img: PropTypes.string
        })
    }).isRequired,
    children: PropTypes.node
};
