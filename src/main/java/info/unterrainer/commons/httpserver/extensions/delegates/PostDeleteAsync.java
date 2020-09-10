package info.unterrainer.commons.httpserver.extensions.delegates;

public interface PostDeleteAsync {

	/**
	 * Allows you to execute code after the deletion of an item.
	 * <p>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param receivedId the ID of the item that was deleted
	 */
	void handle(Long receivedId);
}
