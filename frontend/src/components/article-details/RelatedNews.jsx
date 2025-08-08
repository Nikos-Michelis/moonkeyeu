import React from "react";
import NewsArticle from "@/components/cards/NewsArticle.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faNewspaper } from '@fortawesome/free-solid-svg-icons';

const RelatedNews = ({articles}) =>{
    const contentConfig = {
        styles: {
            wrapper: "article-card--small-wrapper",
            section: "launches-articles",
            card_type: "landscape-card__container--article-card"
        },
    };
    return(
        <section className="related-news-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faNewspaper} />
                <h2>Related News</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex justify-center align-center padding-block-8">
                <div className="container"
                     data-type="full-width"
                     data-spacing="none"
                     data-scroll={articles.length > 2 ? "vertical" : undefined}
                >
                    <div className="margin-block-5 margin-inline-4">
                        <div className="grid-layout__landscape padding-block-2">
                            {articles.length > 0 &&
                                articles.map(article =>
                                    <NewsArticle
                                        key={article.id} {...article}
                                        cardStyles={contentConfig?.styles}
                                        soLaunchBtn={false}
                                    />
                                )
                            }
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default RelatedNews;