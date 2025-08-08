import React from "react";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {useNasaApod} from "@/context/NasaApodProvider.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import {faChevronLeft, faExpand} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {LinkButton} from "@/components/button/LinkButton.jsx";

function NasaApodArticle(){
    const {
        data: nasaApod,
        isPending: isPendingNasaApod,
        isFetching: isFetchingNasaApod,
        isError: isErrorNasaApod
    } = useNasaApod();
    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };
    return(
        <>
            <Head
                title={nasaApod?.title}
                description={nasaApod?.explanation}
                image={nasaApod?.url}
                alt={nasaApod?.title}
                type="article"
            />
            <JsonLdGeneric
                title={nasaApod?.title}
                description={nasaApod?.explanation}
                image={nasaApod?.url}
                alt={nasaApod?.title}
            />
            <ScrollToTop behavior="auto" />
            <SkeletonLoader
                isFetching={isFetchingNasaApod}
                isPending={isPendingNasaApod}
                isError={isErrorNasaApod}
                contentConfig={contentConfig}>
                <section className="article">
                    <div className="container flex justify-center" data-type="wide" data-spacing="none">
                        <div className="container container--light-overlay article__content flex flex-column align-center" data-type="fixed" data-spacing="none">
                            <div className="container flex justify-start padding-block-start-7 padding-block-end-2">
                                <Button
                                    className="btn--transparent"
                                    onClick={() => window.history.back()}
                                >
                                    <FontAwesomeIcon icon={faChevronLeft} /> Back
                                </Button>
                            </div>
                            <div className="container article__overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                <div className="article__image-box article__image-box--nasa-apod">
                                    <LinkButton to={nasaApod?.url} className="btn--transparent hover scale-small pos-absolute right-2 top-2" isExternal={true}>
                                        <FontAwesomeIcon icon={faExpand} />
                                    </LinkButton>
                                    <Img
                                        src={nasaApod?.url}
                                        alt={nasaApod?.title || "default"}
                                        className="article__image article__image--nasa-apod"
                                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                    />
                                </div>
                            </div>
                            <div className="article__info-container container flex flex-column" data-type="full-bleed">
                                <section className="nasa-description-section">
                                    <div className="article__info-box">
                                        <h2>{nasaApod?.title}</h2>
                                        <p>{nasaApod?.explanation}</p>
                                    </div>
                                </section>
                                <div className="padding-block-end-4">
                                    <hr className="hr-90-md"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </SkeletonLoader>
        </>
    )
}
export default NasaApodArticle;