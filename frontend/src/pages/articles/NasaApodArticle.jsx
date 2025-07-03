import React from "react";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {useNasaApod} from "@/context/NasaApodProvider.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import {faChevronLeft} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function NasaApodArticle(){
    const { data: nasaApod, isPending: isPendingNasaApod, isFetching: isFetchingNasaApod, isError: isErrorNasaApod} = useNasaApod();
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
                <section className="article-section">
                    <div className="container __article flex justify-center" data-type="wide" data-spacing="none">
                        <div className="container __article overlay flex flex-column align-center" data-type="fixed" data-spacing="none">
                            <div className="container flex justify-start padding-block-start-7 padding-block-end-2">
                                <Button
                                    className="btn-transparent"
                                    onClick={() => window.history.back()}
                                >
                                    <FontAwesomeIcon icon={faChevronLeft} /> Back
                                </Button>
                            </div>
                            <div className="container __overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                <div className="image-box nasa-apod-img">
                                    <Img
                                        src={nasaApod?.url}
                                        alt={nasaApod?.title || "default"}
                                        className="nasa-apod-img"
                                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                    />
                                </div>
                            </div>
                            <div className="article-info-container container flex flex-column" data-type="full-bleed">
                                <section className="text-section">
                                    <div className="info-box">
                                        <h2>{nasaApod?.title}</h2>
                                        <p>{nasaApod?.explanation}</p>
                                    </div>
                                </section>
                                <div className="padding-block-end-4">
                                    <hr className="hr-6-md"/>
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