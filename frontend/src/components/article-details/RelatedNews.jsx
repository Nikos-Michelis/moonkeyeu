import React from "react";
import NewsArticle from "@/components/cards/NewsArticle.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faNewspaper } from '@fortawesome/free-regular-svg-icons';

const RelatedNews = ({articles}) =>{
    const cardOptions = {
        styles: {
            container: "article-card"
        }
    }
    return(
        <section className="related-news-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faNewspaper} />
                <h2>Related News</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="flex justify-center">
                    <div className={`container flex justify-center ${articles.length > 2 ? "scrolling" : ""}`} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            <div className="landscape-grid padding-block-2">
                                {articles.length > 0 &&
                                    articles.map(article =>
                                        <NewsArticle cardOptions={cardOptions} soLaunchBtn={false} key={article.id} {...article} />
                                    )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default RelatedNews;