// 
// Decompiled by Procyon v0.5.36
// 

package se.michaelthelin.spotify.requests.data.playlists;

import se.michaelthelin.spotify.requests.IRequest;
import se.michaelthelin.spotify.requests.AbstractRequest;
import com.neovisionaries.i18n.CountryCode;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.AbstractDataRequest;

@JsonDeserialize(builder = Builder.class)
public class GetPlaylistRequest extends AbstractDataRequest<Playlist>
{
    private GetPlaylistRequest(final Builder builder) {
        super(builder);
    }
    
    @Override
    public Playlist execute() throws IOException, SpotifyWebApiException, ParseException {
        return new Playlist.JsonUtil().createModelObject(this.getJson());
    }
    
    public static final class Builder extends AbstractDataRequest.Builder<Playlist, Builder>
    {
        public Builder(final String accessToken) {
            super(accessToken);
        }
        
        public Builder playlist_id(final String playlist_id) {
            assert playlist_id != null;
            assert !playlist_id.equals("");
            return ((AbstractRequest.Builder<T, Builder>)this).setPathParameter("playlist_id", playlist_id);
        }
        
        public Builder fields(final String fields) {
            assert fields != null;
            assert !fields.equals("");
            return ((AbstractRequest.Builder<T, Builder>)this).setQueryParameter("fields", fields);
        }
        
        public Builder market(final CountryCode market) {
            assert market != null;
            return ((AbstractRequest.Builder<T, Builder>)this).setQueryParameter("market", market);
        }
        
        public Builder additionalTypes(final String additionalTypes) {
            assert additionalTypes != null;
            assert additionalTypes.matches("((^|,)(episode|track))+$");
            return ((AbstractRequest.Builder<T, Builder>)this).setQueryParameter("additional_types", additionalTypes);
        }
        
        @Override
        public GetPlaylistRequest build() {
            this.setPath("/v1/playlists/{playlist_id}");
            return new GetPlaylistRequest(this, null);
        }
        
        @Override
        protected Builder self() {
            return this;
        }
    }
}
